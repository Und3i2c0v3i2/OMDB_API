package com.example.omdbapi.db;


import com.example.omdbapi.moviedetails.MovieDetails;

import java.util.List;


public class MovieDetailsRepositoryImpl implements MovieDetailsRepository {

    private DBHelper dbHelper;

    public MovieDetailsRepositoryImpl(DBHelper dbHelper) {
        this.dbHelper = dbHelper;

    }

    // CRUD
    @Override
    public int insert(MovieDetails movie) {
        return dbHelper.insert(movie);
    }

    @Override
    public List<MovieDetails> getAll() {
        return dbHelper.getAll();
    }

    @Override
    public int delete(MovieDetails movieDetails) {
        return dbHelper.delete(movieDetails);
    }

    @Override
    public MovieDetails getById(int id) {
        return dbHelper.getById(id);
    }





}
