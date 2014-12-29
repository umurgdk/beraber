package org.beraber.beraber.services;

import android.content.Context;
import android.widget.Toast;

import org.beraber.beraber.events.ActivityEntityEvents;

import de.greenrobot.event.EventBus;

public class ToastNotificationService {
    private final Context context;

    public ToastNotificationService(Context context) {
        this.context = context;
        System.out.println("Toast service started!!!!!!!!!!");
        EventBus.getDefault().register(this);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(ActivityEntityEvents.Posted event) {
        System.out.println("Success event catch!");
        Toast.makeText(context, "New activity created successfully!", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(ActivityEntityEvents.FailedCreate event) {
        System.out.println("Fail event catch!");
        Toast.makeText(context, "Error when creating activity!", Toast.LENGTH_SHORT).show();
    }
}
