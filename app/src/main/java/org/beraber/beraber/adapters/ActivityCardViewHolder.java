package org.beraber.beraber.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.beraber.beraber.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ActivityCardViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.author_name)          protected TextView authorName;
    @InjectView(R.id.author_bio)           protected TextView authorBio;
    @InjectView(R.id.author_avatar)        protected ImageView authorAvatar;

    @InjectView(R.id.activity_image)       protected ImageView activityImage;
    @InjectView(R.id.activity_title)       protected TextView  activityTitle;
    @InjectView(R.id.activity_date)        protected TextView  activityDate;
    @InjectView(R.id.activity_description) protected TextView  activityDescription;

    public ActivityCardViewHolder(View view) {
        super(view);

        ButterKnife.inject(this, view);
    }
}
