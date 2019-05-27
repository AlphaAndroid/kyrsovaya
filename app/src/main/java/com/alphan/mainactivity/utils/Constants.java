package com.alphan.mainactivity.utils;

import android.Manifest;

import com.alphan.mainactivity.R;
import com.alphan.mainactivity.models.CircleCheckBoxModel;

import java.util.ArrayList;

public class Constants {

    //places
    public static final ArrayList<CircleCheckBoxModel> BASE_PLACES = new ArrayList<CircleCheckBoxModel>() {
        {
            add(new CircleCheckBoxModel(
                    true,
                    R.drawable.ic_circle_airport,
                    "Аэропорты",
                    "airport",
                    R.color.colorOne
            ));

            add(new CircleCheckBoxModel(
                    false,
                    R.drawable.ic_circle_atm,
                    "Банкоматы",
                    "atm",
                    R.color.colorTwo
            ));

            add(new CircleCheckBoxModel(
                    false,
                    R.drawable.ic_circle_bar,
                    "Бары",
                    "bar",
                    R.color.colorThree
            ));

            add(new CircleCheckBoxModel(
                    false,
                    R.drawable.ic_circle_hospital,
                    "Больницы",
                    "hospital",
                    R.color.colorFour
            ));

            add(new CircleCheckBoxModel(
                    false,
                    R.drawable.ic_circle_shop,
                    "Супермаркеты",
                    "supermarket",
                    R.color.colorFive
            ));
        }
    };

    //network
    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    public static final String API_KEY = "AIzaSyDCFKv2ubhy6kttlzVPVaANGGU2CuY_HuA";

    //view const
    public static final String GRANT_PERMISSIONS = "Предоставьте необходимые разрешения для использования всех возможностей приложения";
    public static final String OK = "Ok";
    public static final String SEARCH_RADIUS = "Радиус поиска:";

    //permissions
    public static final String[] SET_OF_RIGHTS_TO_GET_MY_LOCATION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    public static final int PERMISSION_REQUEST_CODE = 1000;

    //pref const
    public static final String USER_PREFERENCES = "omgupsPreferences";
    public static final String RADIUS_STATUS = "radiusStatus";
    public static final String RADIUS = "radius";
    public static final String SELECTED_PLACE_TYPE = "selectedPlaceType";
}
