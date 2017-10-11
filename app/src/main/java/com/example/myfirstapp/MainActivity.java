package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.data.SunshinePreferences;
import com.example.myfirstapp.dummy.DummyContent;
import com.example.myfirstapp.utilities.NetworkUtils;
import com.example.myfirstapp.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private TextView mWeatherTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);


        loadWeatherData();


    }

    /*
        This method will get the user's preferred location for weather,
        and then tell some
        backgound method to get the weather data in the background
     */
    private void loadWeatherData(){
        String location = SunshinePreferences.getPerferredWeatherLocation(this);
        new FetchWeatherTask().execute(location);

    } // end loadWeatherData()


    /* Create a class that extends AsyncTask to perform
        network requests
     */
    public class FetchWeatherTask extends  AsyncTask<String, Void, String[]> {


        // perofrm netowrk request
        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0){
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData =
                            OpenWeatherJsonUtils
                            .getSimpleWeatherStringsFromJson(
                                    MainActivity.this,
                                    jsonWeatherResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } // end doInBackground()

        /**
         * method to display results of the network request
         * @param weatherData
         */
        protected void onPostExecute(String[] weatherData){

            /**
             * Iterate through the arrayu and append the Strings to the
             * TextView. The reason why we add the "\n\n\n"
             * after the String is to give visual separation
             * between each String in the TextView. Later, we'll
             * learn about a better way to display lists of data
             */
            for (String weatherString: weatherData) {
                mWeatherTextView.append(weatherString + "\n\n\n") ;
            }



        } // end onPostExecute()




    } // end Class FetchWeatherTask


    /*  Override oonCreateOptionsMenu to
            inflate the menu for this Activity
        Return true to display menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            /* Use AppCompatActivity's method getMenuInflater
                    to get a handle on the menu inflater
             */

        MenuInflater inflater = getMenuInflater();

            /* Use the inflater's inflate method to inflate our
                menu layout to this menu
             */
        inflater.inflate(R.menu.forecast, menu);

            /* Return true so that the menu is displayed
                    in the Toolbar
             */
        return true;

    } // end

    /*  Override onOptionsItemSelected to
            handle clicks on the refresh button

     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            mWeatherTextView.setText("");
            loadWeatherData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    } // end onOptionsItemSelected()










} // end Class MainActivity
