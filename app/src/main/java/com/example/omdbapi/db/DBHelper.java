package com.example.omdbapi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.omdbapi.entity.MovieDetails;
import com.example.omdbapi.entity.Rating;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DBHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "ormlite.db";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN_PRIMARY_ID = "primary_key";
    public static final String COLUMN_FOREIGN_ID = "foreign_key";

    public static final String COLUMN_CUSTOM_ID = "imdbID";


    // TODO define entity classes
    private Dao<MovieDetails, Integer> parentDao;
    private Dao<Rating, Integer> childDao;

    private static DBHelper instance;


    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        try {
            parentDao = getParentDao();
            childDao = getChildDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Rating.class);
            TableUtils.createTable(connectionSource, MovieDetails.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Rating.class, true);
            TableUtils.dropTable(connectionSource, MovieDetails.class, true);
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


    Dao<MovieDetails, Integer> getParentDao() throws SQLException {
        if (parentDao == null) {
            parentDao = getDao(MovieDetails.class);
        }

        return parentDao;
    }

    Dao<Rating, Integer> getChildDao() throws SQLException {
        if (childDao == null) {
            childDao = getDao(Rating.class);
        }

        return childDao;
    }


    @Override
    public void close() {
        parentDao = null;
        childDao = null;
        super.close();
    }


}