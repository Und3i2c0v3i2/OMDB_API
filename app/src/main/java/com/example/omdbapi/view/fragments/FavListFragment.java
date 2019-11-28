package com.example.omdbapi.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omdbapi.App;
import com.example.omdbapi.R;
import com.example.omdbapi.databinding.FragmentFavListBinding;
import com.example.omdbapi.databinding.FragmentListBinding;
import com.example.omdbapi.entity.MovieDetails;
import com.example.omdbapi.view.OnActionPerformedListener;
import com.example.omdbapi.view.adapters.RVAdapterDetails;

import java.util.ArrayList;
import java.util.List;


public class FavListFragment extends Fragment {

    public static final String ARG_LIST = "arg_list";

    private FragmentFavListBinding binding;
    private List<MovieDetails> list;

    private OnActionPerformedListener listener;

    public FavListFragment() {
        // Required empty public constructor
    }


    public static FavListFragment newInstance(List<MovieDetails> list) {
        FavListFragment fragment = new FavListFragment();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav_list, container, false);
        setHasOptionsMenu(true);

        setupAdapter(list);
        return binding.getRoot();
    }


    /* ************* ADAPTER **************** */
    private void setupAdapter(List<MovieDetails> list) {
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RVAdapterDetails adapter = new RVAdapterDetails(list, listener);
        recyclerView.setAdapter(adapter);
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
        list = App.getDbRepository().getAll();
        setupAdapter(list);
    }


}
