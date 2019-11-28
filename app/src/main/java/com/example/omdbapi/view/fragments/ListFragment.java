package com.example.omdbapi.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.omdbapi.R;
import com.example.omdbapi.databinding.FragmentListBinding;
import com.example.omdbapi.entity.Movie;
import com.example.omdbapi.entity.Result;
import com.example.omdbapi.retrofit.RetrofitService;
import com.example.omdbapi.util.BundleFactory;
import com.example.omdbapi.util.ToastUtil;
import com.example.omdbapi.view.OnActionPerformedListener;
import com.example.omdbapi.view.adapters.RVAdapterMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.omdbapi.App.API_KEY;
import static com.example.omdbapi.retrofit.APIConstants.HEADER_KEY;
import static com.example.omdbapi.retrofit.APIConstants.QUERYMAP_KEY_SEARCH;
import static com.example.omdbapi.view.OnActionPerformedListener.OPEN_FAV;
import static com.example.omdbapi.view.OnActionPerformedListener.OPEN_SETTINGS;


public class ListFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_LIST = "arg_list";

    private FragmentListBinding binding;
    private List<Movie> list;

    private OnActionPerformedListener listener;

    public ListFragment() {
        // Required empty public constructor
    }


    public static ListFragment newInstance(List<Movie> list) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST, (ArrayList<? extends Parcelable>) list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getParcelableArrayList(ARG_LIST);
        }
    }


    private String queryTxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);

        binding.searchViewLayout.setClickHandler(this);
        binding.searchViewLayout.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queryTxt = newText;
                return true;
            }
        });

        setupAdapter(list);
        return binding.getRoot();
    }


    /* ************* ADAPTER **************** */
    private void setupAdapter(List<Movie> list) {
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RVAdapterMaster adapter = new RVAdapterMaster(list, listener);
        recyclerView.setAdapter(adapter);
    }


    /* ************* TOOLBAR & MENU ************** */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_settings:
                listener.onActionPerformed(BundleFactory.bundleId(OPEN_SETTINGS, -1));
                return true;

            case R.id.menu_fav:
                listener.onActionPerformed(BundleFactory.bundleId(OPEN_FAV, -1));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /* ******* LIFE CYCLE ******* */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActionPerformedListener) {
            listener = (OnActionPerformedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActionPerformedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
//        list = getActivity().repository.getAll();
//        setupAdapter(list);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btn_search) {
            getAll(getActivity(), queryTxt);
            binding.searchViewLayout.searchView.setQuery("", false);
        }
    }

    public void getAll(final Context context, String query) {

        HashMap<String, String> key = new HashMap<>();
        key.put(HEADER_KEY, API_KEY);
        HashMap<String, String> search = new HashMap<>();
        search.put(QUERYMAP_KEY_SEARCH, query);

        Call<Result> call = RetrofitService.getService().getAll(key, search);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.i("TAG", response.code() + "");
                if (response.code() == 200) {
                    Result result = response.body();
                    List<Movie> list = result.getSearch();
                    setupAdapter(list);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                ToastUtil.showToast(context, t.getMessage());
            }
        });

    }


}
