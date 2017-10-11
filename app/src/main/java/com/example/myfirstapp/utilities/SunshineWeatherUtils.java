package com.example.myfirstapp.utilities;

/**
 * Created by Rashid Saleem on 10/10/2017.
 */

import android.content.Context;

import com.example.myfirstapp.R;
import com.example.myfirstapp.data.SunshinePreferences;

/**
 * Contains useful utilities for a weather app,
 * such as conversion between Celsius and Fahrenheit,
 * from kph to mph, and from degrees to NSEW.
 * It also contains the mapping of weather condition
 * codes in OpenWeatherMap to strings.
 * These strings are contained
 */

public final class SunshineWeatherUtils {


    /**
     * This method will convert a temperature from Celsius to
     * Fahrenheit.
     *
     * @param temperatureInCelsius Temperature in degrees Celsius(C)
     *
     * @return Temperature in degrees Fahrenheit (F)
     */
    private static double celsiusToFahrenheit(
                        double temperatureInCelsius) {
        double temperatureInFahreheit =
                (temperatureInCelsius * 1.8) + 32;
        return temperatureInFahreheit;

    } // end celsiusToFahrenheit()

    /**
     * Temperature data is tored in Celsius by our app.
     * Depending on the user's preference,
     * the app may need to display the temperature in
     * Fahrenheit. This method will perform that
     * temperature conversion if necessary.
     * It will also fromat the temperature so that no
     * decimal points show. Temperatures will be
     * formatted to the following form: "21 C"
     *
     * @param context Android Context to access preferences
     *          and resources
     *
     * @param temperature Temperature in degrees Celsius ( C)
     *
     * @return Formatted temperature String in the following
     * form: "21 C"
     */
    public static String formatTemperature(
            Context context, double temperature) {
        int temperatureFormatResourceId =
                R.string.format_temperature_fahrenheit;

        if (!SunshinePreferences.isMetric(context)) {
            temperature = celsiusToFahrenheit(temperature);
            temperatureFormatResourceId =
                    R.string.format_temperature_fahrenheit;
        }

        /* For presentation, assume the use doesn't care
        about tenths of a degree.
         */
        return String.format(
                context.getString(temperatureFormatResourceId),
                temperature);
    } // end formatTemperature

    /**
     * This method will format the temperatures to be displayed
     * in the
     * following form:"HIGH°C / LOW°C"
     *
     * @param context Android Context to access preferences and
     *                resources
     * @param high    High temperature for a day in user's
     * @param low     Low temperature for a day in user's
     *      preferred units
     *
     * @return String in the form: "HIGH°C / LOW°C"
     */
    public static String formatHighLows(Context context,
                                    double high, double low){
        long roundedHigh = Math.round(high);
        long roundedfLow = Math.round(low);

        String formattedHigh = formatTemperature(context,
                                    roundedHigh);

        String formattedLow = formatTemperature(context,
                                    roundedfLow);

        String highLowStr = formattedHigh + " / " + formattedLow;
        return highLowStr;
    } // end formatHighLows()






}
