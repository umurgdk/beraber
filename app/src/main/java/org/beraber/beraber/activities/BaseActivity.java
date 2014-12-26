package org.beraber.beraber.activities;

import org.beraber.beraber.modules.ActivityScopeModule;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends org.beraber.androidbasestructer.BaseActivity {
    @Override
    protected Object[] getActivityModules() {
        List<Object> modules = new ArrayList<Object>();
        modules.add(new ActivityScopeModule(this));
        return modules.toArray();
    }
}
