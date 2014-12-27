package org.beraber.beraber;

import org.beraber.androidbasestructer.DaggerApplication;

import java.util.Collections;
import java.util.List;

public class BeraberApp extends DaggerApplication {
    @Override
    protected List<Object> getAppModules() {
        return Collections.<Object>singletonList(new BeraberAppModule());
    }
}