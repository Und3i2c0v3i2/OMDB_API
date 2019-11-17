package com.example.omdbapi.retrofit;

import com.example.omdbapi.movielist.Search;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MovieListEndpoints {


    @GET("/")
    Call<Search> getData(@QueryMap HashMap<String, String> key, @QueryMap HashMap<String, String> search);

}
