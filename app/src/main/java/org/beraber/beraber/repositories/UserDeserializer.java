package org.beraber.beraber.repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.beraber.beraber.daos.User;

import java.lang.reflect.Type;

public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder().create();

        JsonObject obj = json.getAsJsonObject();

        long server_id = obj.get("id").getAsLong();
        obj.remove("id");
        obj.addProperty("server_id", server_id);

        return gson.fromJson(obj, User.class);
    }
}
