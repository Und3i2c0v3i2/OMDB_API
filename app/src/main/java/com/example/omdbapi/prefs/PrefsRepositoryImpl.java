package com.example.omdbapi.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;


public class PrefsRepositoryImpl {

    public static final String SPLASH_PREF_KEY = "splash_key";

    private SharedPreferences sharedPreferences;


    public PrefsRepositoryImpl(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean checkBooleanPrefs(String key) {
        return sharedPreferences.getBoolean(key, false);
    }


}
