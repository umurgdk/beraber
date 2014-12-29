package org.beraber.beraber.repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.beraber.beraber.daos.Activity;
import org.beraber.beraber.daos.User;

import java.lang.reflect.Type;

public class ActivityDeserializer implements JsonDeserializer<Activity> {

    @Override
    public Activity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        JsonObject obj = json.getAsJsonObject();
        long server_id = obj.get("id").getAsLong();
        obj.remove("id");
        obj.addProperty("server_id", server_id);

        Activity activity = gson.fromJson(obj, Activity.class);

        if (obj.has("user")) {
            JsonObject objUser = obj.getAsJsonObject("user");
            User user = gson.fromJson(objUser, User.class);

            activity.setUser(user);
        }

        return activity;
    }
}
