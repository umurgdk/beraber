package org.beraber.beraber.repositories;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.beraber.beraber.daos.Activity;
import org.beraber.beraber.daos.User;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityDeserializer implements JsonDeserializer<Activity> {

    @Override
    public Activity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date result = df.parse(obj.get("start_date").getAsString());

            Activity activity = new Activity(
                    obj.get("id").getAsLong(),
                    obj.get("title").getAsString(),
                    obj.get("description").getAsString(),
                    result,
                    obj.get("user_id").getAsLong());

            if (obj.has("user")) {
                JsonObject objUser = obj.getAsJsonObject("user");
                User user = new User(
                        objUser.get("id").getAsLong(),
                        objUser.get("name").getAsString(),
                        objUser.get("bio").getAsString()
                );

                activity.setUser(user);
            }

            return activity;

        } catch (ParseException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}
