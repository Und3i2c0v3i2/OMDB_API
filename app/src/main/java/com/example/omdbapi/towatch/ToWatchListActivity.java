package com.example.omdbapi.towatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.omdbapi.App;
import com.example.omdbapi.R;
import com.example.omdbapi.databinding.ActivityToWatchListBinding;
import com.example.omdbapi.moviedetails.MovieDetails;
import com.example.omdbapi.db.MovieDetailsRepository;

import java.util.List;

import static com.example.omdbapi.towatch.ToWatchListAdapter.ID;

public class ToWatchListActivity extends AppCompatActivity implements OnActionPerformedListenerToWatch {

    private ActivityToWatchListBinding binding;
    private MovieDetailsRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_to_watch_list);
        repository = App.getMovieDetailsRepository();

        setupToolBar();
        getFromDb();
    }


    public void getFromDb() {
        List<MovieDetails> list = repository.getAll();
        setupAdapter(list);
    }


    /* ************* TOOLBAR & MENU ************** */
    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;

    }


    /* ************* ADAPTER ************** */
    private void setupAdapter(List<MovieDetails> list) {

        binding.recyclerViewToWatch.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewToWatch.setHasFixedSize(true);
        ToWatchListAdapter adapter = new ToWatchListAdapter(list, this);
        binding.recyclerViewToWatch.setAdapter(adapter);
    }



    /* ************* LISTENER ************** */
    @Override
    public void onActionPerformed(Bundle bundle) {

        int id = bundle.getInt(ID);
        Intent intent = new Intent(this, ToWatchDetailsActivity.class);
        intent.putExtra(ID, id);

        startActivity(intent);
    }



    /* ************* LIFE CYCLE ************** */
    @Override
    protected void onResume() {
        super.onResume();
        getFromDb();
    }
}
