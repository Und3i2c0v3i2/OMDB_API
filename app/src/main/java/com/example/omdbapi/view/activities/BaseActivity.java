package com.example.omdbapi.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.omdbapi.App;
import com.example.omdbapi.R;
import com.example.omdbapi.prefs.PrefsRepositoryImpl;


public class BaseActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {


    /* for fragment transaction backstack so we can navigate */
    public static final String HOME_FRAGMENT = "home_fragment";
    public static final String DETAILS_FRAGMENT = "details_fragment";


    protected PrefsRepositoryImpl prefsRepository;

    protected AlertDialog aboutDialog;
    protected AlertDialog confirmationDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefsRepository = App.getPrefsRepository();
    }



    /* ************* FRAGMENT TRANSACTION *********** */

    protected void fragmentTransaction(Fragment fragment, String addToBackStack) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(addToBackStack)
                .commit();
    }







}
