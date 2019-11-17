package com.example.omdbapi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.omdbapi.moviedetails.MovieDetails;
import com.example.omdbapi.moviedetails.Rating;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DBHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "ormlite.db";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_MOVIE_DETAILS_ID = "movieDetails_id";

    private Dao<MovieDetails, Integer> movieDetailsDao;
    private Dao<Rating, Integer> ratingDao;

    private static DBHelper instance;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        try {
            movieDetailsDao = getMovieDetailsDao();
            ratingDao = getRatingDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, MovieDetails.class);
            TableUtils.createTable(connectionSource, Rating.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, MovieDetails.class, true);
            TableUtils.dropTable(connectionSource, Rating.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBHelper getInstance(Context context) {

        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }


    public Dao<MovieDetails, Integer> getMovieDetailsDao() throws SQLException {
        if (movieDetailsDao == null) {
            movieDetailsDao = getDao(MovieDetails.class);
        }

        return movieDetailsDao;
    }

    public Dao<Rating, Integer> getRatingDao() throws SQLException {
        if (ratingDao == null) {
            ratingDao = getDao(Rating.class);
        }

        return ratingDao;
    }


    @Override
    public void close() {
        movieDetailsDao = null;
        ratingDao = null;
        super.close();
    }

    public int insert(MovieDetails movieDetails) {

        int i = -1;
        if (movieDetails != null) {

            if(checkIfExists(movieDetails) == null) {

                try {
                    i = movieDetailsDao.create(movieDetails);
                    for (Rating r : movieDetails.getRatings()) {
                        r.setMovieDetails(movieDetails);
                        ratingDao.create(r);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return i;
    }

    private MovieDetails checkIfExists(MovieDetails movieDetails) {

        MovieDetails exist = null;

        try {
            QueryBuilder<MovieDetails, Integer> qb = movieDetailsDao.queryBuilder();
            Where<MovieDetails, Integer> where = qb.where();
            where.eq("imdbId", movieDetails.getImdbID());
            PreparedQuery<MovieDetails> pq = qb.prepare();
            exist = movieDetailsDao.queryForFirst(pq);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }


    public MovieDetails getById(int id) {

        try {
            List<Rating> list = getByMovieId(id);
            MovieDetails movieDetails = movieDetailsDao.queryForId(id);
            movieDetails.setRatings(list);
            return movieDetails;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }


    public List<MovieDetails> getAll() {

        List<MovieDetails> list = new ArrayList<>();
        try {
            list = movieDetailsDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public int delete(MovieDetails movieDetails) {

        int i = -1;

        try {
            i = movieDetailsDao.delete(movieDetails);
            deleteByMovieId(movieDetails.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }

    public List<Rating> getByMovieId(int id) {

        List<Rating> list = new ArrayList<>();
        try {
            list = ratingDao.queryForEq(COLUMN_MOVIE_DETAILS_ID, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int deleteByMovieId(int id) {

        int i = -1;

        try {

            DeleteBuilder builder = ratingDao.deleteBuilder();
            builder.where()
                    .eq("movieDetails_id", id);
            builder.delete();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }




}