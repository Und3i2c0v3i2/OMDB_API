package com.example.omdbapi.retrofit;


import com.example.omdbapi.entity.MovieDetails;
import com.example.omdbapi.entity.Result;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static com.example.omdbapi.retrofit.APIConstants.ENDPOINT_ALL;


public interface ServiceEndpoints {


    @GET(ENDPOINT_ALL)
    Call<Result> getAll(@QueryMap HashMap<String, String> key,
                        @QueryMap HashMap<String, String> search);

    @GET(ENDPOINT_ALL)
    Call<MovieDetails> getById(@QueryMap HashMap<String, String> key,
                               @QueryMap HashMap<String, String> search);


}
