package com.example.omdbapi.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.omdbapi.App;
import com.example.omdbapi.R;
import com.example.omdbapi.databinding.FragmentDetailsBinding;
import com.example.omdbapi.entity.MovieDetails;
import com.example.omdbapi.entity.Movie;
import com.example.omdbapi.retrofit.RetrofitService;
import com.example.omdbapi.util.ToastUtil;
import com.example.omdbapi.view.OnActionPerformedListener;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.omdbapi.App.API_KEY;
import static com.example.omdbapi.retrofit.APIConstants.HEADER_KEY;
import static com.example.omdbapi.retrofit.APIConstants.QUERYMAP_KEY_ID;


public class DetailsFragment extends Fragment {


    private static final String ARG_OBJECT = "object";

    private Movie o;
    private OnActionPerformedListener listener;

    private FragmentDetailsBinding binding;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Movie o) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_OBJECT, o);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            o = getArguments().getParcelable(ARG_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        setHasOptionsMenu(true);
        binding.setVar(o);


        return binding.getRoot();

    }


    /* ************* TOOLBAR & MENU ************** */

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_other, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_to_watch:
                getById(getActivity(), binding.getVar().getImdbID());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

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

    public void getById(final Context context, String query) {

        HashMap<String, String> key = new HashMap<>();
        key.put(HEADER_KEY, API_KEY);
        HashMap<String, String> search = new HashMap<>();
        search.put(QUERYMAP_KEY_ID, query);

        Call<MovieDetails> call = RetrofitService.getService().getById(key, search);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.code() == 200) {
                    MovieDetails result = response.body();
                    App.getDbRepository().insert(result);
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                ToastUtil.showToast(context, t.getMessage());
            }
        });

    }

}
