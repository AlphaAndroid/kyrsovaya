package com.alphan.mainactivity.utils;

import android.Manifest;

public class Constants {

    //permissions
    public static final String[] SET_OF_RIGHTS_TO_GET_MY_LOCATION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    //pref const
    public static final String USER_PREFERENCES = "omgupsPreferences";
    public static final String RADIUS = "radius";
}
