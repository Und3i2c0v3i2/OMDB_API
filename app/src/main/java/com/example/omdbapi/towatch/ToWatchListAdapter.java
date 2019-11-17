package com.example.omdbapi.towatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.omdbapi.R;
import com.example.omdbapi.databinding.HolderToWatchBinding;
import com.example.omdbapi.moviedetails.MovieDetails;

import java.util.List;


public class ToWatchListAdapter extends Adapter<ToWatchListAdapter.ViewHolder> {


    public static final String ID = "id";

    private List<MovieDetails> list;
    private OnActionPerformedListenerToWatch listener;


    public ToWatchListAdapter(List<MovieDetails> list, OnActionPerformedListenerToWatch listener) {
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HolderToWatchBinding binding = DataBindingUtil.inflate(inflater, R.layout.holder_to_watch, parent, false);

        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MovieDetails movie = list.get(position);
        holder.holderBinding.setM(movie);

    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        HolderToWatchBinding holderBinding;

        public ViewHolder(HolderToWatchBinding holderBinding) {
            super(holderBinding.getRoot());
            this.holderBinding = holderBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putInt(ID, list.get(getAdapterPosition()).getId());

                    listener.onActionPerformed(bundle);
                }
            });
        }
    }


}
