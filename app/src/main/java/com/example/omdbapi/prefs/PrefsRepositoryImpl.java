package com.example.omdbapi.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.Preference;
import androidx.preference.PreferenceManager;


public class PrefsRepositoryImpl implements PrefsRepository {

    private SharedPreferences sharedPreferences;


    public PrefsRepositoryImpl(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
   }


    @Override
    public boolean isEnabled() {
        return sharedPreferences.getBoolean(SPLASH_SCREEN_KEY, false);
    }


    @Override
    public void savePrefs(Preference pref, String key) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, pref.getSummary().toString());
        editor.apply();
    }

}
