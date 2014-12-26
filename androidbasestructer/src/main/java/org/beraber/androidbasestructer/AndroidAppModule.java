package org.beraber.androidbasestructer;

import android.content.Context;
//import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        library = true
)
public class AndroidAppModule {
    /* package */ static Context applicationContext;

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return applicationContext;
    }

//    @Provides
//    @Singleton
//    LocationManager provideLocationManager() {
//        return (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
//    }
}
