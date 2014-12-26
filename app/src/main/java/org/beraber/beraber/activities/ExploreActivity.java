package org.beraber.beraber.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.beraber.beraber.R;
import org.beraber.beraber.adapters.ActivityCardAdapter;
import org.beraber.beraber.entities.Activity;
import org.beraber.beraber.helpers.ActivityViewHelper;
import org.beraber.beraber.helpers.AuthorViewHelper;
import org.beraber.beraber.services.ActivitiesApiService;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

@EActivity
public class ExploreActivity extends BaseActivity {
    @InjectView(R.id.activitiesList)
    RecyclerView activitiesList;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.loadingLayout)
    View loadingView;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    ActivitiesApiService activitiesService;

    @Inject
    AuthorViewHelper authorViewHelper;

    @Inject
    ActivityViewHelper activityViewHelper;

    ActivityCardAdapter activitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        ButterKnife.inject(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        fab.setImageDrawable(new IconDrawable(this, Iconify.IconValue.md_add).sizeDp(24).color(Color.WHITE));
        fab.attachToRecyclerView(activitiesList);

        prepareActivities();
    }

    private void openActivityDetailIntent(Activity activity) {
        Intent intent = new Intent(this, ActivityDetailActivity.class);
        intent.putExtra(ActivityDetailActivity.EXTRA_ACTIVITY_DATA, activity);
    }

    private void prepareActivities() {
        activitiesList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        activitiesList.setLayoutManager(llm);

        loadingView.setVisibility(View.VISIBLE);
        fetchActivities();
    }

    @Click(R.id.fab)
    void fabClicked() {
        Intent intent = new Intent(this, NewActivityActivity_.class);
        startActivity(intent);
    }

    @Background
    void fetchActivities() {
        List<Activity> activities = activitiesService.listActivities();
        activitiesAdapter = new ActivityCardAdapter(
                activities,
                this,
                authorViewHelper,
                activityViewHelper);

        showActivities();
    }

    @UiThread
    void showActivities() {
        activitiesList.setAdapter(activitiesAdapter);
        loadingView.setVisibility(View.GONE);
        activitiesList.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_explore, menu);
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
