package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.data.SunshinePreferences;
import com.example.myfirstapp.dummy.DummyContent;
import com.example.myfirstapp.utilities.NetworkUtils;

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

                String[] simpleJsonWeatherData = OpenWeatherJsonUrl

            } catch (IOException e) {
                e.printStackTrace();
            }

            return new String[0];
        }
    }










}
