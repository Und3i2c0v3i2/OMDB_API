package com.example.omdbapi;

import android.app.Application;

import com.example.omdbapi.db.DBHelper;
import com.example.omdbapi.db.MovieDetailsRepository;
import com.example.omdbapi.db.MovieDetailsRepositoryImpl;


public class App extends Application {


    public static final String OMDB_KEY = BuildConfig.OMDB_KEY;

    private static MovieDetailsRepository movieDetailsRepository;


    @Override
    public void onCreate() {
        super.onCreate();

        DBHelper dbHelper = DBHelper.getInstance(this);
        movieDetailsRepository = new MovieDetailsRepositoryImpl(dbHelper);
    }


    public static MovieDetailsRepository getMovieDetailsRepository() {
        return movieDetailsRepository;
    }


}
