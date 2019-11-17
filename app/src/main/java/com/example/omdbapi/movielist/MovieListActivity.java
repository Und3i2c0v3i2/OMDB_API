package com.example.omdbapi.movielist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.omdbapi.R;
import com.example.omdbapi.databinding.ActivityListBinding;
import com.example.omdbapi.moviedetails.MovieDetailsActivity;
import com.example.omdbapi.prefs.PrefsActivity;
import com.example.omdbapi.retrofit.RetrofitService;
import com.example.omdbapi.towatch.ToWatchListActivity;
import com.example.omdbapi.util.ToastUtil;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.omdbapi.App.OMDB_KEY;
import static com.example.omdbapi.retrofit.APIConstants.API_KEY;
import static com.example.omdbapi.retrofit.APIConstants.QUERY;
import static com.example.omdbapi.retrofit.APIConstants.SEARCH_KEY;


public class MovieListActivity
        extends AppCompatActivity
        implements OnActionPerformedListener, View.OnClickListener {



    private ActivityListBinding binding;
    private String query;
    private List<Movie> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.searchLayout.setClickListener(this);


        if(savedInstanceState != null) {
            query = savedInstanceState.getString(QUERY);
        }

        setupToolBar();

    }


    private void doSearch(String query) {
        HashMap<String, String> key = new HashMap<>();
        key.put(API_KEY, OMDB_KEY);
        HashMap<String, String> search = new HashMap<>();
        search.put(SEARCH_KEY, query);

        Call<Search> call = RetrofitService.getMasterService().getData(key, search);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {

                if (response.code() == 200) {
                    Search search = response.body();
                    list = search.getSearch();

                    showAll(list);
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                ToastUtil.showToast(MovieListActivity.this, t.getMessage());
            }
        });

    }


    public void showAll(List<Movie> list) {
        setupAdapter(list);
    }


    /* ************* TOOLBAR & MENU ************** */
    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.settings:
                startActivity(new Intent(this, PrefsActivity.class));
                return true;

            case R.id.watch:
                Intent intent = new Intent(this, ToWatchListActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /* ************* ADAPTER ************** */
    private void setupAdapter(List<Movie> list) {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        MovieListAdapter adapter = new MovieListAdapter(list, this);
        binding.recyclerView.setAdapter(adapter);
    }


    /* ************* LISTENER ************** */
    @Override
    public void onActionPerformed(Bundle bundle) {

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        query = binding.searchLayout.search.getQuery().toString();
        binding.searchLayout.search.setQuery("", false);

        doSearch(query);
    }



    /* ************* LIFE CYCLE ************** */
    @Override
    protected void onResume() {
        super.onResume();

        if (query != null)
            doSearch(query);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QUERY, query);
    }

}
