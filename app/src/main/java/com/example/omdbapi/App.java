package com.example.omdbapi;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.omdbapi.db.DBHelper;
import com.example.omdbapi.db.DBRepositoryImpl;
import com.example.omdbapi.prefs.PrefsRepositoryImpl;

public class App extends Application {


    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String CHANNEL_ID = "channel";
    public static final String CHANNEL_NAME = "Notification Channel";
    public static final String CHANNEL_DESC = "Notification Channel Description";

    private static DBRepositoryImpl dbRepository;
    private static PrefsRepositoryImpl prefsRepository;


    @Override
    public void onCreate() {
        super.onCreate();

        DBHelper dbHelper = DBHelper.getInstance(this);
        dbRepository = new DBRepositoryImpl(dbHelper);
        registerNotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_DESC);
        prefsRepository = new PrefsRepositoryImpl(this);

    }


    public static DBRepositoryImpl getDbRepository() {
        return dbRepository;
    }

    public static PrefsRepositoryImpl getPrefsRepository() {
        return prefsRepository;
    }



    public void registerNotificationChannel(String channelId, String channelName, String channelDescription) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);

            NotificationManager manager = this.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }


}
