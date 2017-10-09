package com.example.myfirstapp.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Rashid Saleem on 10/9/2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String DYNAMIC_WEATHER_URL =
            "https://andfun-weather.udacity.com/weather";

    private static final String STATIC_WEATHER_URL =
            "https://andfun-weather.udacity.com/staticweather";

    private static final String FORECAST_BASE_URL = STATIC_WEATHER_URL;


    // The format we want our API to return
    private static final String format = "json";
    // units we want our API to return
    private static final String units = "metric";
    // The number of days we want our API to return
    private static final int numDays = 14;

    final static String QUERY_PARAM = "q";
    final static String LAT_PARAM = "lat";
    final static String LON_PARAM = "lon";
    final static String FORMAT_PARAM = "mode";
    final static String UNITS_PARAM = "units";
    final static String DAYS_PARAM  = "cnt";





    /*
        Builds the URL used to talk to the weather server using a location.

        @param locationQuery The location that will be queried for
        @return The URL to use to query the wather server.
     */

    public static URL buildUrl(String locationQuery){

        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, locationQuery)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;


    } // end BuildURi() method

    /*
        Builds URL to talk to the wather server using Latitute and Longitude

        @param lat and lon   (latitutde , longitude)
        @return The URL to use to query the weather server.
     */
    private static URL buildUrl(Double lat, Double lon) {
        // This will be implemented in a future lesson
        return null;
    }

    /*
        This method return the entire result from the HTTP response

        @param url The URL to fetch HTTP response from.
        @return The content of the HTTP respnose.
        @throws IOEXception Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }



    }





} // end NetworkUtils Class
