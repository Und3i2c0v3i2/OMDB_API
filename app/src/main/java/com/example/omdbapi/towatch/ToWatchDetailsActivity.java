package com.example.omdbapi.towatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.omdbapi.App;
import com.example.omdbapi.R;
import com.example.omdbapi.databinding.ActivityToWatchDetailsBinding;
import com.example.omdbapi.moviedetails.MovieDetails;
import com.example.omdbapi.db.MovieDetailsRepository;
import com.example.omdbapi.util.ToastUtil;

import static com.example.omdbapi.towatch.ToWatchListAdapter.ID;

public class ToWatchDetailsActivity extends AppCompatActivity{

    private int id;
    private MovieDetailsRepository repository;
    private MovieDetails movieDetails;
    private ActivityToWatchDetailsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_to_watch_details);
        repository = App.getMovieDetailsRepository();

        if(getIntent() != null) {
            id = getIntent().getIntExtra(ID, -1);
            getSelected(id);
        }

        setupToolBar();
    }

    private void getSelected(int id) {
        movieDetails = repository.getById(id);
        showSelected(movieDetails);
    }

    private void showSelected(MovieDetails movieDetails) {
        binding.detailsToWatchLayout.setM(movieDetails);
    }

    /* ************* TOOLBAR & MENU ************** */
    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_to_watch_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.seen:
                int i = repository.delete(movieDetails);
                if(i != -1) {
                    ToastUtil.showToast(this, "Movie has been removed from the list");
                }
                return true;
        }

        return false;

    }


}
