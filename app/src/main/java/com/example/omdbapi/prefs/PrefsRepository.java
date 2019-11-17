package com.example.omdbapi.prefs;

import androidx.preference.Preference;

public interface PrefsRepository {


    public static final String SPLASH_SCREEN_KEY = "splash_screen_key";


    void savePrefs(Preference pref, String key);

    boolean isEnabled();

}
