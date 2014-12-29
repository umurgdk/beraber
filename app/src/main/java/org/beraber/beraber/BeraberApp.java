package org.beraber.beraber;

import org.beraber.androidbasestructer.DaggerApplication;
import org.beraber.beraber.services.ToastNotificationService;
import org.beraber.beraber.services.UserSyncService;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class BeraberApp extends DaggerApplication {
    @Inject
    ToastNotificationService toastNotificationService;

    @Inject
    UserSyncService userSyncService;

    public BeraberApp() {
        super();

        EventBus.builder()
                .throwSubscriberException(true)
                .logNoSubscriberMessages(true)
                .installDefaultEventBus();
    }

    @Override
    protected List<Object> getAppModules() {
        return Collections.<Object>singletonList(new BeraberAppModule());
    }
}