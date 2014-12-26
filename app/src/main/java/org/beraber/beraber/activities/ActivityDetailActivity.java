package org.beraber.beraber.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.beraber.beraber.R;
import org.beraber.beraber.entities.Activity;
import org.beraber.beraber.helpers.ActivityViewHelper;
import org.beraber.beraber.helpers.AuthorViewHelper;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

@EActivity
public class ActivityDetailActivity extends BaseActivity {
    public static final String EXTRA_ACTIVITY_DATA = "EXTRA_ACTIVITY_DATA";

    @Inject
    AuthorViewHelper authorViewHelper;

    @Inject
    ActivityViewHelper activityViewHelper;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.activity_detail_title)
    TextView activityTitle;

    @InjectView(R.id.activity_detail_description)
    TextView activityDescription;

    @InjectView(R.id.author_avatar)
    ImageView authorAvatar;

    @InjectView(R.id.author_name)
    TextView authorName;

    @InjectView(R.id.author_bio)
    TextView authorBio;

    @InjectView(R.id.activity_date)
    TextView activityDate;

    Activity activityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);

        // Populate views
        ButterKnife.inject(this);

        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activityData = (Activity) extras.getParcelable(EXTRA_ACTIVITY_DATA);
            fillUI();
        }
    }

    private void fillUI() {
        activityTitle.setText(activityData.title);
        activityDescription.setText(activityData.description);
        authorName.setText(activityData.user.name);
        authorBio.setText(activityData.user.bio);
        authorAvatar.setImageDrawable(authorViewHelper.getAvatarDrawable(this, activityData.user));
        activityDate.setText(activityViewHelper.formatDate(activityData.startDate));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
