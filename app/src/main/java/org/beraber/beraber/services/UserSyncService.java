package org.beraber.beraber.services;

import android.os.CountDownTimer;

import com.path.android.jobqueue.JobManager;

import org.beraber.beraber.events.ActivityEntityEvents;
import org.beraber.beraber.jobs.FetchUsersJob;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.greenrobot.event.EventBus;

public class UserSyncService {
    JobManager jobManager;

    private Set<Long> user_ids;

    public UserSyncService(JobManager jobManager) {
        this.jobManager = jobManager;
        EventBus.getDefault().register(this);
    }

    private void startFetchForUserId(long userId) {
        if (user_ids == null) {
            user_ids = new HashSet<Long>();
            user_ids.add(userId);

            new CountDownTimer(300, 300) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    jobManager.addJobInBackground(new FetchUsersJob(user_ids));
                    user_ids = null;
                }
            }.start();
        } else {
            user_ids.add(userId);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(ActivityEntityEvents.Posted event) {
        startFetchForUserId(event.activity.getUser_id());
    }
}
