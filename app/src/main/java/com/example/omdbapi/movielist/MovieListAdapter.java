package com.example.omdbapi.movielist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.omdbapi.databinding.HolderMoviesBinding;
import com.example.omdbapi.R;

import java.util.List;

import static com.example.omdbapi.movielist.OnActionPerformedListener.MOVIE_PARCELABLE;


public class MovieListAdapter extends Adapter<MovieListAdapter.ViewHolder> {


    private List<Movie> list;
    private OnActionPerformedListener listener;

    public MovieListAdapter(List<Movie> list, OnActionPerformedListener listener) {
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HolderMoviesBinding binding = DataBindingUtil.inflate(inflater, R.layout.holder_movies, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movie movie = list.get(position);
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

        HolderMoviesBinding holderBinding;

        public ViewHolder(HolderMoviesBinding holderBinding) {
            super(holderBinding.getRoot());
            this.holderBinding = holderBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(MOVIE_PARCELABLE, list.get(getAdapterPosition()));

                    listener.onActionPerformed(bundle);
                }
            });
        }
    }


}
