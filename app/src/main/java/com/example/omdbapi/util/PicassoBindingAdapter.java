package com.example.omdbapi.util;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;


public class PicassoBindingAdapter {

    /* binding adapter method for loading images with picasso; custom xml tag : loadImg */
    @androidx.databinding.BindingAdapter("loadImg")
    public static void bindImg(ImageView view, String imgUrl) {
        Picasso.get()
                .load(imgUrl)
                .fit()
                .into(view);
    }


}
