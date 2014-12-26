package org.beraber.beraber.modules;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;

import org.beraber.androidbasestructer.ForActivity;
import org.beraber.beraber.BeraberAppModule;
import org.beraber.beraber.activities.ActivityDetailActivity_;
import org.beraber.beraber.activities.BaseActivity;
import org.beraber.beraber.activities.ExploreActivity_;
import org.beraber.beraber.activities.MainActivity;
import org.beraber.beraber.activities.NewActivityActivity_;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        library = true,
        addsTo = BeraberAppModule.class,
        injects = {
                MainActivity.class,
                ExploreActivity_.class,
                ActivityDetailActivity_.class,
                NewActivityActivity_.class
        }
)
public class ActivityScopeModule {
    private final BaseActivity activity;

    public ActivityScopeModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    @ForActivity
    Context providesActivityContext() {
        return activity;
    }

    @Provides
    @Singleton
    ActionBarActivity providesActivity() {
        return activity;
    }
}
