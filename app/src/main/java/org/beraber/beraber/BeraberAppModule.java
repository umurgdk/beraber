package org.beraber.beraber;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.beraber.androidbasestructer.AndroidAppModule;
import org.beraber.androidbasestructer.ForApplication;
import org.beraber.beraber.daos.Activity;
import org.beraber.beraber.daos.DaoMaster;
import org.beraber.beraber.daos.DaoSession;
import org.beraber.beraber.helpers.ActivityViewHelper;
import org.beraber.beraber.helpers.AuthorViewHelper;
import org.beraber.beraber.helpers.retrofit.RootedGsonConverter;
import org.beraber.beraber.repositories.ActivityDeserializer;
import org.beraber.beraber.repositories.ActivityRepository;
import org.beraber.beraber.repositories.UserRepository;
import org.beraber.beraber.services.ActivitiesApiService;
import org.beraber.beraber.services.AuthorApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

@Module(
        library = true,
        addsTo = AndroidAppModule.class,
        injects = {
                BeraberApp.class
        }
)
public class BeraberAppModule {
    @Provides @Singleton
    RestAdapter provideRestAdapter() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .registerTypeAdapter(Activity.class, new ActivityDeserializer())
                .create();

        return new RestAdapter.Builder()
                .setEndpoint("http://beraber.t.proxylocal.com")
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
    AuthorApiService provideAuthorApiService(RestAdapter adapter) {
        return adapter.create(AuthorApiService.class);
    }

    @Provides @Singleton
    ActivitiesApiService provideActivitiesApiService(RestAdapter adapter) {
        return adapter.create(ActivitiesApiService.class);
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
    ActivityRepository provideActivityRepository(DaoSession session) {
        return new ActivityRepository(session);
    }

    @Provides @Singleton
    UserRepository provideUserRepository(DaoSession session) {
        return new UserRepository(session);
    }
}
