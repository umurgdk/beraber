package org.beraber.beraber.jobs;

import android.util.SparseArray;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import org.beraber.beraber.daos.User;
import org.beraber.beraber.events.UserEntityEvents;
import org.beraber.beraber.helpers.entities.UserEntityHelper;
import org.beraber.beraber.repositories.UserRepository;
import org.beraber.beraber.services.UserApiService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class FetchUsersJob extends Job {
    @Inject
    transient UserRepository userRepository;

    @Inject
    transient UserApiService userApiService;

    @Inject
    transient UserEntityHelper userEntityHelper;

    transient List<Long> user_ids;

    public FetchUsersJob(Collection<Long> user_ids) {
        super(new Params(Priorities.MID).requireNetwork());

        this.user_ids = new ArrayList<Long>(user_ids);
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        List<User> users = userApiService.getUsers(user_ids);

        List<User> updated_users = new ArrayList<User>();

        for (User user : users) {
            User dbUser = userRepository.getByServerId(user.getServer_id());

            if (dbUser != null) {
                userEntityHelper.updateNotNull(dbUser, user);
                userRepository.insertOrUpdate(dbUser);
                updated_users.add(dbUser);
            } else {
                userRepository.insertOrUpdate(user);
                updated_users.add(user);
            }
        }

        EventBus.getDefault().post(new UserEntityEvents.FetchedBatch(updated_users));
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
