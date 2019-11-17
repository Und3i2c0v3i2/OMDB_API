package com.example.omdbapi.db;


import com.example.omdbapi.moviedetails.MovieDetails;

import java.util.List;


public interface MovieDetailsRepository {

    int insert(MovieDetails movie);
    int delete(MovieDetails movie);
    MovieDetails getById(int id);
    List<MovieDetails> getAll();
}
