package com.example.omdbapi.retrofit;

import com.example.omdbapi.moviedetails.MovieDetails;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MovieDetailsEndpoints {

    @GET("/")
    Call<MovieDetails> getSelected(@QueryMap HashMap<String, String> key, @QueryMap HashMap<String, String> search);
}
