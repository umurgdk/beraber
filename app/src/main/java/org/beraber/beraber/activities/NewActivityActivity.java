package org.beraber.beraber.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.beraber.beraber.R;
import org.beraber.beraber.daos.Activity;
import org.beraber.beraber.daos.User;
import org.beraber.beraber.repositories.ActivityRepository;
import org.beraber.beraber.repositories.UserRepository;
import org.beraber.beraber.services.ActivitiesApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RetrofitError;

@EActivity
public class NewActivityActivity extends BaseActivity
implements DatePickerDialog.OnDateSetListener
{
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.form_startDate)
    EditText startDate;

    @InjectView(R.id.form_title)
    EditText title;

    @InjectView(R.id.form_description)
    EditText description;

    @SystemService
    InputMethodManager im;

    @Inject
    ActivitiesApiService activitiesApiService;

    @Inject
    ActivityRepository activityRepository;

    @Inject
    UserRepository userRepository;

    DatePickerDialog datePicker;
    Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity);

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

        Calendar cal = Calendar.getInstance();

        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnFocusChangeListener(focusListener);
        startDate.setOnClickListener(clickListener);

        datePicker = new DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) + 1);
    }

    public void createActivity() {
        User user = new User(1l);

        Activity activity = new Activity();
        activity.setTitle(title.getText().toString());
        activity.setDescription(description.getText().toString());
        activity.setUser(user);
        activity.setStart_date(selectedDate);

        onActivitySaved();
        saveActivity(activity);
    }

    @Background
    void saveActivity(Activity activity) {
        try {
            Activity newActivity = activitiesApiService.createActivity(activity);
            User user = newActivity.getUser();

            userRepository.insertOrUpdate(user);
            activityRepository.insertOrUpdate(newActivity);
        } catch (RetrofitError e) {
            onActivitySaveError();
        }
    }

    @UiThread
    void onActivitySaveError() {
        Toast.makeText(getApplicationContext(), "Error when saving your activity", Toast.LENGTH_LONG).show();
    }

    @UiThread
    void onActivitySaved() {
        Toast.makeText(getApplicationContext(), "Your activity has been saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);

        selectedDate = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        startDate.setText(df.format(selectedDate));
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePicker.show();
        }
    };

    private View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {
            if (hasFocus)
            {
                datePicker.show();
            }
        }
    };

    // -- MENU --
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_activity, menu);
        menu.findItem(R.id.action_check).setIcon(
            new IconDrawable(this, Iconify.IconValue.md_check).actionBarSize().color(Color.WHITE)
        ).setTitle("SAVE");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_check) {
            createActivity();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
