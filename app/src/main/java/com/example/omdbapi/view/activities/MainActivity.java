package com.example.omdbapi.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.omdbapi.App;
import com.example.omdbapi.R;
import com.example.omdbapi.databinding.ActivityMainBinding;
import com.example.omdbapi.entity.Movie;
import com.example.omdbapi.entity.MovieDetails;
import com.example.omdbapi.prefs.PrefsFragment;
import com.example.omdbapi.view.OnActionPerformedListener;
import com.example.omdbapi.view.fragments.DetailsFragment;
import com.example.omdbapi.view.fragments.FavDetailsFragment;
import com.example.omdbapi.view.fragments.FavListFragment;
import com.example.omdbapi.view.fragments.ListFragment;

import java.util.List;
import java.util.Objects;


public class MainActivity
        extends BaseActivity
        implements OnActionPerformedListener {

    private ActivityMainBinding binding;
    private Toolbar toolbar;

    private List<Movie> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupToolbar();

        fragmentTransaction(ListFragment.newInstance(list), HOME_FRAGMENT);

    }



    /* ************* TOOLBAR & MENU ************** */

    private void setupToolbar() {
        toolbar = (Toolbar) binding.toolbar;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }



    /* ************************ ACTION LISTENER ************** */

    @Override
    public void onActionPerformed(Bundle bundle) {

        int bundleKey = bundle.getInt(BUNDLE_KEY, -1);

        switch (bundleKey) {

            case OPEN_DETAILS:
                Movie m = bundle.getParcelable(OBJECT_PARCELABLE);
                getSupportFragmentManager().popBackStack(HOME_FRAGMENT, 0);
                fragmentTransaction(DetailsFragment.newInstance(m), DETAILS_FRAGMENT);
                break;

            case OPEN_SETTINGS:
                fragmentTransaction(new PrefsFragment(), null);
                break;

            case OPEN_FAV:
                List<MovieDetails> list = App.getDbRepository().getAll();
                fragmentTransaction(FavListFragment.newInstance(list), null);
                break;

            case OPEN_FAV_DETAILS:
                MovieDetails movieDetails = bundle.getParcelable(OBJECT_PARCELABLE);
                fragmentTransaction(FavDetailsFragment.newInstance(movieDetails), null);
                break;

        }
    }


}
