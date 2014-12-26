package org.beraber.beraber.services;

import org.beraber.beraber.entities.User;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface AuthorApiService {
    @GET("/authors")
    List<User> listAuthors();

    @GET("/authors/{id}")
    User getAuthor(@Path("id") long id);
}
