package com.example.omdbapi.retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static Retrofit getRetrofitInstance(){

        return new Retrofit.Builder()
                .baseUrl(APIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static MovieListEndpoints getMasterService(){
        return getRetrofitInstance().create(MovieListEndpoints.class);
    }

    public static MovieDetailsEndpoints getDetailsService(){
        return getRetrofitInstance().create(MovieDetailsEndpoints.class);
    }
}
