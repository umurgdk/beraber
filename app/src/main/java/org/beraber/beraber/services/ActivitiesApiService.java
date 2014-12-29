package org.beraber.beraber.services;

import org.beraber.beraber.daos.Activity;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ActivitiesApiService {
    @GET("/activities")
    List<Activity> listActivities();

    @GET("/activities/{id}")
    Activity getActivity(@Path("id") long id);

    @POST("/activities")
    Activity createActivity(@Body Activity activity);
}
