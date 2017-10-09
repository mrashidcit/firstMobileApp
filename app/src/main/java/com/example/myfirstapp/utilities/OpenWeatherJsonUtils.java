package com.example.myfirstapp.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Utility function to handle OpenWeatherMap JSON data.
 */


public final class OpenWeatherJsonUtils {
    /*
        this method parses JSON from a web response and
        returns an array of Strings
        describing the weather over various days from the forecast.


        @param forecastJsonStr JSON response from server
        @return Array of Strings describing weather data
        @throws JSONException IF JSON data cannot be properly parsed

     */
    public static String[] getSimpleWeatherStringsFromJson(Context context, String forecastJsonStr)
                           throws JSONException {

        /*
            Weather information. Each day's forecast info is
            an element of the "list" array
         */
        final String OWM_LIST = "list";

        /* All temperatures are children of the "temp" object */
        final String OWM_TEMPERATURE = "temp";

        // Max temperature for the day
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";

        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";
        final String OWM_MESSAGE_CODE = "cod";

        // String array to hold each day's wather String
        String[] parsedWeatherData = null;

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        // Is there an error?
        if (forecastJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    // lOCATION INVALID
                default:
                   // Server probably down
                    return null;
            }
        } // end if

        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        parsedWeatherData = new String[weatherArray.length()];

        long localDate = System.currentTimeMillis();
//        long utcDate = SunshineDateUtils.get
//        long startDay = SunshineDateUtils






    }



}

