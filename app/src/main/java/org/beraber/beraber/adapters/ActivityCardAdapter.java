package org.beraber.beraber.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.beraber.beraber.R;
import org.beraber.beraber.activities.ActivityDetailActivity;
import org.beraber.beraber.activities.ActivityDetailActivity_;
import org.beraber.beraber.daos.Activity;
import org.beraber.beraber.daos.User;
import org.beraber.beraber.helpers.ActivityViewHelper;
import org.beraber.beraber.helpers.AuthorViewHelper;

import java.util.ArrayList;
import java.util.List;

public class ActivityCardAdapter extends RecyclerView.Adapter<ActivityCardViewHolder> {
    private List<Activity> activityList;

    private final AuthorViewHelper authorViewHelper;
    private final ActivityViewHelper activityViewHelper;
    private final Context context;

    public ActivityCardAdapter(@Nullable List<Activity> activityList,
                               Context context,
                               AuthorViewHelper authorViewHelper,
                               ActivityViewHelper activityViewHelper) {
        super();

        if (activityList != null) {
            this.activityList = activityList;
        } else {
            this.activityList = new ArrayList<>();
        }

        this.authorViewHelper = authorViewHelper;
        this.activityViewHelper = activityViewHelper;
        this.context = context;
    }

    public Activity getItem(int position) {
        return activityList.get(position);
    }

    public void replace(List<Activity> newList) {
        activityList = newList;
        notifyDataSetChanged();
    }

    @Override
    public ActivityCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_explore_item, parent, false);

        return new ActivityCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityCardViewHolder holder, int position) {
        final Activity activity = activityList.get(position);

        User user = activity.getUser();

        if (user != null) {
            holder.authorAvatar.setImageDrawable(authorViewHelper.getAvatarDrawable(context, user));
            holder.authorName.setText(user.getName());
            holder.authorBio.setText(user.getBio());
        }

        holder.activityTitle.setText(activity.getTitle());

        if (activity.getStart_date() != null) {
            holder.activityDate.setText(activityViewHelper.formatDate(activity.getStart_date()));
        }

        holder.activityDescription.setText(activity.getDescription());
        holder.activityImage.setImageDrawable(activityViewHelper.getImageDrawable(context, activity));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDetailActivity(activity);
            }
        });
    }

    public void startDetailActivity(Activity activity) {
        Intent intent = new Intent(this.context, ActivityDetailActivity_.class);
        intent.putExtra(ActivityDetailActivity.EXTRA_ACTIVITY_ID, activity.getId());
        this.context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }
}
