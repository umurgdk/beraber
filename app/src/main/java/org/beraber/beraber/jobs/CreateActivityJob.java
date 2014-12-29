package org.beraber.beraber.jobs;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import org.beraber.beraber.daos.Activity;
import org.beraber.beraber.events.ActivityEntityEvents;
import org.beraber.beraber.helpers.entities.ActivityEntityHelper;
import org.beraber.beraber.repositories.ActivityRepository;
import org.beraber.beraber.services.ActivitiesApiService;

import java.util.Date;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;

public class CreateActivityJob extends Job {
    @Inject
    ActivityRepository activityRepository;

    @Inject
    ActivitiesApiService activitiesApiService;

    @Inject
    ActivityEntityHelper activityEntityHelper;

    private long local_id;
    private String title;
    private String description;
    private long   user_id;
    private Date start_date;


    public CreateActivityJob(String title, String description, long user_id, Date start_date) {
        super(new Params(Priorities.HIGH).requireNetwork().persist());

        local_id = -System.currentTimeMillis();
        this.title = title;
        this.description = description;
        this.user_id = user_id;
        this.start_date = start_date;
    }

    @Override
    public void onAdded() {
        Activity activity = new Activity(local_id, title, description, null, start_date, user_id);
        activityRepository.insertOrUpdate(activity);
        EventBus.getDefault().post(new ActivityEntityEvents.Posting(activity, local_id));
    }

    @Override
    public void onRun() throws Throwable {
        Activity postActivity = new Activity();
        postActivity.setTitle(title);
        postActivity.setStart_date(start_date);
        postActivity.setDescription(description);
        postActivity.setUser_id(user_id);

        Activity newActivity = activitiesApiService.createActivity(postActivity);
        Activity existingActivity = activityRepository.getById(local_id);

        if (existingActivity != null) {
            activityEntityHelper.updateNotNull(existingActivity, newActivity);
            activityRepository.insertOrUpdate(existingActivity);
            EventBus.getDefault().post(new ActivityEntityEvents.Posted(existingActivity, local_id));
        } else {
            activityRepository.insertOrUpdate(newActivity);
            EventBus.getDefault().post(new ActivityEntityEvents.Posted(newActivity, newActivity.getId()));
        }
    }

    @Override
    protected void onCancel() {
        Activity localActivity = activityRepository.getById(local_id);
        if (localActivity != null) {
            activityRepository.deleteById(local_id);
            EventBus.getDefault().post(new ActivityEntityEvents.Deleted(local_id));
        }

        EventBus.getDefault().post(new ActivityEntityEvents.FailedCreate());
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        if (throwable instanceof RetrofitError) {
            RetrofitError error = (RetrofitError) throwable;
            int status = error.getResponse().getStatus();

            return status < 400 || status > 499;
        }

        return true;
    }
}
