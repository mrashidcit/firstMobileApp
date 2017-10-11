package com.example.myfirstapp.data;

import android.content.Context;

/**
 * Created by Rashid Saleem on 10/9/2017.
 */

public class SunshinePreferences {

    // Human readable location string
    public static final String REF_CITY_NAME = "city_name";


    /*
        In order to uniquery pinpoint the location on the map when we
        map intent, we store the latitude and longitude
     */
    public static final String PREF_COORD_LAT = "coord_lat";
    public static final String PREF_COORD_LONG = "coord_long";

    private static final String DEFAULT_WEATHER_LOCATION = "94043, USA";
    private static final double[] DEFAULT_WEATHER_COORDINATES = {37.4284, 122.0724};

    private static final String DEFAULT_MAP_LOCATION =
            "1600 Amphitheatre Parkway, Mountain View, CA 94043";


    public static String getPerferredWeatherLocation(Context context){

        return getDefaultWeatherLocation();

    }

    public static double[] getLocationCoordinates(Context context){
        return getDefaultWeatherCoordinates();
    }

    public static boolean isLocationLatLonAvailable(Context context) {
//         will be implemented in a future lesson
        return false;
    }




    private static String getDefaultWeatherLocation(){
        return DEFAULT_WEATHER_LOCATION;

    }

    public static double[] getDefaultWeatherCoordinates(){

        return DEFAULT_WEATHER_COORDINATES;
    }

    public static boolean isMetric(Context context){
//        will be implemented in future lesson
        return true;
    }











}
