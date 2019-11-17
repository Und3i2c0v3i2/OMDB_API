package com.example.omdbapi.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.omdbapi.R;
import com.example.omdbapi.movielist.MovieListActivity;
import com.example.omdbapi.prefs.PrefsRepository;
import com.example.omdbapi.prefs.PrefsRepositoryImpl;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PrefsRepository prefsRepository = new PrefsRepositoryImpl(this);

        if(prefsRepository.isEnabled()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MovieListActivity.class));
                    // don't go back to splash
                    finish();
                }
            }, 5000);
        } else {
            startActivity(new Intent(SplashActivity.this, MovieListActivity.class));
            // don't go back to splash
            finish();
        }


    }
}
