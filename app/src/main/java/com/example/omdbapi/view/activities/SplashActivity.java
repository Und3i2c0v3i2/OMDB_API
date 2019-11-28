package com.example.omdbapi.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.omdbapi.App;
import com.example.omdbapi.R;
import com.example.omdbapi.prefs.PrefsRepositoryImpl;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkPrefs();
    }

    public void checkPrefs() {
        boolean b = App.getPrefsRepository().checkBooleanPrefs(PrefsRepositoryImpl.SPLASH_PREF_KEY);
        if (b) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    // don't go back to splash
                    finish();
                }
            }, 5000);
        } else {

            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
