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
        @throws JSONException IF JSON data cannot be
            properly parsed

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
        final String OWM_DESCRIPTION = "forecast";
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
        long utcDate = SunshineDateUtils.getUTCDateFromLocal(localDate);
        long startDay = SunshineDateUtils.normalizeDate(utcDate);

        for (int i = 0; i < weatherArray.length(); i++){
            String date;
            String highAndLow;

            // These are the values that will be collected
            long dateTimeMillis;
            double high;
            double low;
            String description;

            // Get JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            /**
             * We igonre all the datetime values embedded in the
             * JSON and assume that
             * the values are returned in-order by day
             * (which is not guaranteed to be correct).
             */
            dateTimeMillis = startDay + SunshineDateUtils.DAY_IN_MILLIS * i;
            date = SunshineDateUtils.getFriendlyDateString(context,
                                    dateTimeMillis, false);

            /**
             * Description is in a child array called "weather",
             * shich is 1 element long.
             * That elment also contains a weather code.
             *
             */
            JSONObject weatherObject = dayForecast
                    .getJSONArray(OWM_WEATHER)
                    .getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            /**
             * Temperatures are send by Open Weather Map in a
             * child object called "temp".
             *
             * Editor's Note: Try not to name variables "temp"
             * when working with temperature.
             * It confuses everybody. Temp could easily mean
             * any number of things, including
             * temperature, temporary and is just a bad variable name
             */
            JSONObject temperatureObject = dayForecast
                        .getJSONObject(OWM_TEMPERATURE);
            high = temperatureObject.getDouble(OWM_MAX);
            low = temperatureObject.getDouble(OWM_MIN);
            highAndLow = SunshineWeatherUtils
                        .formatHighLows(context, high, low);

            parsedWeatherData[i] = date + " - " + description
                                        + " - " + highAndLow;


        } // end for() loop


        return parsedWeatherData;


    }



}

