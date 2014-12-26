package org.beraber.beraber;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.beraber.androidbasestructer.AndroidAppModule;
import org.beraber.beraber.helpers.ActivityViewHelper;
import org.beraber.beraber.helpers.AuthorViewHelper;
import org.beraber.beraber.helpers.retrofit.RootedGsonConverter;
import org.beraber.beraber.services.ActivitiesApiService;
import org.beraber.beraber.services.AuthorApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

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
                .create();

        return new RestAdapter.Builder()
                .setEndpoint("http://beraber.t.proxylocal.com")
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
}
