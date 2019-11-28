package com.example.omdbapi.db;

import com.example.omdbapi.entity.MovieDetails;
import com.example.omdbapi.entity.Rating;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.omdbapi.db.DBHelper.COLUMN_CUSTOM_ID;
import static com.example.omdbapi.db.DBHelper.COLUMN_FOREIGN_ID;


public class DBRepositoryImpl {

    private Dao<MovieDetails, Integer> parentDao;
    private Dao<Rating, Integer> childDao;


    public DBRepositoryImpl(DBHelper dbHelper) {
        try {
            parentDao = dbHelper.getParentDao();
            childDao = dbHelper.getChildDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public int insert(MovieDetails movie) {

        int i = 0;
        if (movie != null) {
            if (checkIfExists(movie) == null) {
                try {
                    i = parentDao.create(movie);
                    for(Rating c : movie.getRatings()) {
                        c.setMovieDetails(movie);
                        childDao.create(c);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return i;
    }


    public int delete(MovieDetails movie) {

        int i = 0;
        try {
            i = parentDao.delete(movie);
            deleteForeignCollection(movie.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }


    public List<MovieDetails> getAll() {
        List<MovieDetails> list = new ArrayList<>();
        try {
            list = parentDao.queryForAll();
            for(MovieDetails m : list) {
                List<Rating> children = getForeignCollection(m.getId());
                m.setRatings(children);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public MovieDetails getById(int id) {
        MovieDetails movie = null;
        try {
            movie = parentDao.queryForId(id);
            getForeignCollection(movie.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return movie;
    }



    public List<Rating> getForeignCollection(int id) {
        List<Rating> list = new ArrayList<>();

        try {
            list = childDao.queryForEq(COLUMN_FOREIGN_ID, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public void deleteForeignCollection(int id) {
        try {
            DeleteBuilder builder = childDao.deleteBuilder();
            builder.where()
                    .eq(COLUMN_FOREIGN_ID, id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private MovieDetails checkIfExists(MovieDetails movie) {

        MovieDetails exist = null;

        try {
            QueryBuilder<MovieDetails, Integer> qb = parentDao.queryBuilder();
            Where<MovieDetails, Integer> where = qb.where();
            where.eq(COLUMN_CUSTOM_ID, movie.getImdbID());
            PreparedQuery<MovieDetails> pq = qb.prepare();
            exist = parentDao.queryForFirst(pq);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }


}
