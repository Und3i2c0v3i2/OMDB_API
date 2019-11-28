package com.example.omdbapi.view.fragments;

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
import com.example.omdbapi.databinding.FragmentFavDetailsBinding;
import com.example.omdbapi.entity.MovieDetails;
import com.example.omdbapi.util.ToastUtil;
import com.example.omdbapi.view.OnActionPerformedListener;


public class FavDetailsFragment extends Fragment {


    private static final String ARG_OBJECT = "object";

    private MovieDetails o;
    private OnActionPerformedListener listener;

    private FragmentFavDetailsBinding binding;


    public FavDetailsFragment() {
        // Required empty public constructor
    }

    public static FavDetailsFragment newInstance(MovieDetails o) {
        FavDetailsFragment fragment = new FavDetailsFragment();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav_details, container, false);
        setHasOptionsMenu(true);
        binding.setVar(o);


        return binding.getRoot();

    }


    /* ************* TOOLBAR & MENU ************** */

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fav, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_seen:
                App.getDbRepository().delete(binding.getVar());
                ToastUtil.showToast(getActivity(), "Removed");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
