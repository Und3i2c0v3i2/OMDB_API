package com.example.omdbapi.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omdbapi.R;
import com.example.omdbapi.databinding.HolderDetailsBinding;
import com.example.omdbapi.entity.MovieDetails;
import com.example.omdbapi.util.BundleFactory;
import com.example.omdbapi.view.OnActionPerformedListener;

import java.util.List;

import static com.example.omdbapi.view.OnActionPerformedListener.OPEN_FAV_DETAILS;

public class RVAdapterDetails extends RecyclerView.Adapter<RVAdapterDetails.ViewHolder> {

    private List<MovieDetails> list;
    private OnActionPerformedListener listener;

    public RVAdapterDetails(List<MovieDetails> list, OnActionPerformedListener listener) {
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HolderDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.holder_details, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MovieDetails movieDetails = list.get(position);
        holder.holderBinding.setVar(movieDetails);

    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        //
        HolderDetailsBinding holderBinding;

        //
        public ViewHolder(HolderDetailsBinding holderBinding) {
            super(holderBinding.getRoot());
            this.holderBinding = holderBinding;
            this.holderBinding.setClickHandler(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.holder) {
                listener.onActionPerformed(BundleFactory.bundleObj(OPEN_FAV_DETAILS, list.get(getAdapterPosition())));
            }
        }
    }


}