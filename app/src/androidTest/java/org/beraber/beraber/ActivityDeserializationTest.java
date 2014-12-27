package org.beraber.beraber;

import android.test.InstrumentationTestCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.beraber.beraber.daos.Activity;
import org.beraber.beraber.repositories.ActivityDeserializer;

import java.util.Calendar;
import java.util.Date;

public class ActivityDeserializationTest extends InstrumentationTestCase {
    public void testGsonShouldDeserializeActivity() throws Exception{
        String json = "{\"id\":1,\"title\":\"Angular.JS Workshop\",\"description\":\"Angular.JS ogrenmek isteyenler bulusup bir workshop duzenleyelim\",\"image\":null,\"start_date\":\"2015-01-04\",\"end_date\":null,\"created_at\":\"2014-12-25T16:53:01.669Z\",\"updated_at\":\"2014-12-26T01:02:26.151Z\",\"user_id\":1,\"user\":{\"id\":1,\"name\":\"Umur Gedik\",\"bio\":\"Software developer\",\"avatar\":null,\"created_at\":\"2014-12-25T16:58:16.907Z\",\"updated_at\":\"2014-12-25T16:58:16.907Z\"}}";

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .registerTypeAdapter(Activity.class, new ActivityDeserializer())
                .create();

        Activity activity = gson.fromJson(json, Activity.class);

        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 4);
        Date startDate = cal.getTime();

        assertEquals(activity.getTitle(), "Angular.JS Workshop");
        assertEquals(activity.getDescription(), "Angular.JS ogrenmek isteyenler bulusup bir workshop duzenleyelim");
        assertTrue(activity.getStart_date().getTime() - startDate.getTime() < 1000);
        assertEquals(activity.getUser_id(), 1);
        assertEquals(activity.getUser().getName(), "Umur Gedik");
    }
}
