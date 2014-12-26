package org.beraber.androidbasestructer;

import dagger.ObjectGraph;

public interface Injector {

    void inject(Object object);

    ObjectGraph getObjectGraph();
}
