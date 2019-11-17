package com.example.omdbapi.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("loadImg")
    public static void bindImg(ImageView view, String poster) {
        Picasso.get()
                .load(poster)
                .fit()
                .into(view);
    }
}
