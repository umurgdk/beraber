package org.beraber.beraber.services;

import org.beraber.beraber.daos.User;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface UserApiService {
    @GET("/users")
    List<User> listUsers();

    @GET("/users/{id}")
    User getUser(@Path("id") long id);

    @GET("/users")
    List<User> getUsers(@Query("user_ids") List<Long> ids);
}
