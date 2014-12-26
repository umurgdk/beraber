package org.beraber.androidbasestructer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import dagger.ObjectGraph;

public abstract class BaseActivity extends ActionBarActivity
        implements Injector {

    private ObjectGraph activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerApplication application = (DaggerApplication) getApplication();
        activityGraph = application.getObjectGraph().plus(getActivityModules());

        activityGraph.inject(this);
    }

    @Override
    protected void onDestroy() {
        activityGraph = null;

        super.onDestroy();
    }

    @Override
    public void inject(Object object) {
        activityGraph.inject(object);
    }

    public ObjectGraph getObjectGraph() {
        return activityGraph;
    }

    protected abstract Object[] getActivityModules();
}
