package org.beraber.androidbasestructer;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

public abstract class DaggerApplication extends Application implements Injector {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidAppModule sharedAppModule = new AndroidAppModule();
        sharedAppModule.applicationContext = this.getApplicationContext();

        List<Object> modules = new ArrayList<Object>();
        modules.add(sharedAppModule);
        modules.addAll(getAppModules());

        objectGraph = ObjectGraph.create(modules.toArray());
        objectGraph.inject(this);
    }

    protected abstract List<Object> getAppModules();

    @Override
    public void inject(Object object) {
        objectGraph.inject(object);
    }

    @Override
    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
