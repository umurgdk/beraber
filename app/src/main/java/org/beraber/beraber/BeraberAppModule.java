package org.beraber.beraber;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.path.android.jobqueue.BaseJob;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.path.android.jobqueue.log.CustomLogger;

import org.beraber.androidbasestructer.AndroidAppModule;
import org.beraber.androidbasestructer.DaggerApplication;
import org.beraber.androidbasestructer.ForApplication;
import org.beraber.beraber.daos.Activity;
import org.beraber.beraber.daos.DaoMaster;
import org.beraber.beraber.daos.DaoSession;
import org.beraber.beraber.daos.User;
import org.beraber.beraber.helpers.ActivityViewHelper;
import org.beraber.beraber.helpers.AuthorViewHelper;
import org.beraber.beraber.helpers.entities.ActivityEntityHelper;
import org.beraber.beraber.helpers.entities.UserEntityHelper;
import org.beraber.beraber.helpers.retrofit.RootedGsonConverter;
import org.beraber.beraber.jobs.CreateActivityJob;
import org.beraber.beraber.jobs.FetchUsersJob;
import org.beraber.beraber.repositories.ActivityDeserializer;
import org.beraber.beraber.repositories.ActivityRepository;
import org.beraber.beraber.repositories.UserDeserializer;
import org.beraber.beraber.repositories.UserRepository;
import org.beraber.beraber.services.ActivitiesApiService;
import org.beraber.beraber.services.UserApiService;
import org.beraber.beraber.services.ToastNotificationService;
import org.beraber.beraber.services.UserSyncService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

@Module(
        library = true,
        addsTo = AndroidAppModule.class,
        injects = {
                BeraberApp.class,
                CreateActivityJob.class,
                FetchUsersJob.class
        }
)
public class BeraberAppModule {
    @Provides @Singleton
    RestAdapter provideRestAdapter() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .registerTypeAdapter(Activity.class, new ActivityDeserializer())
                .registerTypeAdapter(User.class, new UserDeserializer())
                .create();

        return new RestAdapter.Builder()
                .setEndpoint("http://berabers.t.proxylocal.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("RETROFIT_LOGS"))
                .setConverter(new RootedGsonConverter(gson))
                .build();
    }

    @Provides @Singleton
    ActivityViewHelper provideActivityViewHelper() {
        return new ActivityViewHelper();
    }

    @Provides @Singleton
    AuthorViewHelper provideAuthorViewHelper() {
        return new AuthorViewHelper();
    }

    @Provides @Singleton
    UserApiService provideAuthorApiService(RestAdapter adapter) {
        return adapter.create(UserApiService.class);
    }

    @Provides @Singleton
    ActivitiesApiService provideActivitiesApiService(RestAdapter adapter) {
        return adapter.create(ActivitiesApiService.class);
    }

    @Provides @Singleton
    JobManager provideJobManager(@ForApplication final Context context) {
        return new JobManager(context, new Configuration.Builder(context).injector(new DependencyInjector() {
            @Override
            public void inject(BaseJob job) {
                DaggerApplication app = (DaggerApplication)context.getApplicationContext();
                app.inject(job);
            }
        }).customLogger(new CustomLogger() {
            private static final String TAG = "JOBS";

            @Override
            public boolean isDebugEnabled() {
                return true;
            }

            @Override
            public void d(String text, Object... args) {
                Log.d(TAG, String.format(text, args));
            }

            @Override
            public void e(Throwable t, String text, Object... args) {
                Log.e(TAG, text, t);
//                Log.d(TAG, String.format(text, args));
            }

            @Override
            public void e(String text, Object... args) {
                Log.d(TAG, String.format(text, args));
            }
        }).minConsumerCount(1).build());
    }

    @Provides @Singleton
    ToastNotificationService toastNotificationService(@ForApplication final Context context) {
        return new ToastNotificationService(context);
    }

    // Database Integration
    @Provides @Singleton
    DaoSession provideDaoSession(@ForApplication Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "beraber-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    @Provides @Singleton
    ActivityEntityHelper provideActivityEntityHelper() {
        return new ActivityEntityHelper();
    }

    @Provides @Singleton
    UserEntityHelper provideUserEntityHelper() {
        return new UserEntityHelper();
    }

    @Provides @Singleton
    ActivityRepository provideActivityRepository(DaoSession session) {
        return new ActivityRepository(session);
    }

    @Provides @Singleton
    UserRepository provideUserRepository(DaoSession session) {
        return new UserRepository(session);
    }

    @Provides @Singleton
    UserSyncService provideUserSyncService(JobManager jobManager) {
        return new UserSyncService(jobManager);
    }
}
