package com.example.omdbapi.util;

import android.os.Bundle;
import android.os.Parcelable;

public class BundleFactory {

    public static final String BUNDLE_KEY = "bundle_key";
    public static final String OBJECT_PARCELABLE = "object_parcelable";
    public static final String OBJECT_ID = "id";


    public static Bundle bundleObj(int action, Parcelable o) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY, action);
        bundle.putParcelable(OBJECT_PARCELABLE, o);

        return bundle;
    }

    public static Bundle bundleId(int action, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY, action);
        bundle.putInt(OBJECT_ID, id);

        return bundle;
    }

    public static Bundle bundleString(int action, String id) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY, action);
        bundle.putString(OBJECT_ID, id);

        return bundle;
    }
}
