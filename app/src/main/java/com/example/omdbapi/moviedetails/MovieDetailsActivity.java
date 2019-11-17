package com.example.omdbapi.moviedetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.omdbapi.App;
import com.example.omdbapi.R;
import com.example.omdbapi.db.MovieDetailsRepository;
import com.example.omdbapi.databinding.ActivityDetailsBinding;
import com.example.omdbapi.movielist.Movie;
import com.example.omdbapi.retrofit.RetrofitService;
import com.example.omdbapi.util.ToastUtil;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.omdbapi.App.OMDB_KEY;
import static com.example.omdbapi.movielist.OnActionPerformedListener.MOVIE_PARCELABLE;
import static com.example.omdbapi.retrofit.APIConstants.API_KEY;
import static com.example.omdbapi.retrofit.APIConstants.INFO_KEY;


public class MovieDetailsActivity extends AppCompatActivity {


    private ActivityDetailsBinding binding;
    private Movie movie;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        if (getIntent() != null) {
            movie = getIntent().getParcelableExtra(MOVIE_PARCELABLE);
        }

        setupToolBar();
        showSelected(movie);

    }


    public void showSelected(Movie param) {
        binding.detailsLayout.setM(param);
    }

    public void getSelected(String param) {

        HashMap<String, String> key = new HashMap<>();
        key.put(API_KEY, OMDB_KEY);
        HashMap<String, String> search = new HashMap<>();
        search.put(INFO_KEY, param);

        Call<MovieDetails> call = RetrofitService.getDetailsService().getSelected(key, search);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                if (response.code() == 200) {
                    MovieDetails selected = response.body();
                    MovieDetailsRepository repository = App.getMovieDetailsRepository();
                    int i = repository.insert(selected);
                    if(i == -1) {
                        ToastUtil.showToast(MovieDetailsActivity.this, "You've already added this movie to watch later list");
                    }

                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }



    /* ************* TOOLBAR & MENU ************** */
    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.watch_later:
                getSelected(movie.getImdbID());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }




}
