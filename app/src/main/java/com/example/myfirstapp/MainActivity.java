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

import com.example.myfirstapp.dummy.DummyContent;
import com.example.myfirstapp.utilities.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;

    // reference to error Message Textview
    private TextView mErrorMessageDisplay;

    // reference to the ProgressBar
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Testign JSON Object Code

        JSONObject json = new JSONObject();

        // Temperature Object
        JSONObject temp = new JSONObject();
        try {
            temp.put("max", 19.01);
            temp.put("min", 11.34);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject weather = new JSONObject();

        try {
            weather.put("id", 801);
            weather.put("condition", "Clouds");
            weather.put("description", "few clouds");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json.put("pressure", "1023.51");
            json.put("humidity", "87");

            json.put("temp", temp);
            json.put("weather", weather);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("Student", json.toString());

        String condition = new String();
        JSONObject weather2 = new JSONObject();

        try {
            Log.d("Student", json.toString());
//            weather2.put("weather", json.getJSONObject("weather")) ;
            weather2 = (JSONObject) json.getJSONObject("weather");

            condition = weather2.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }





            // Testing JSON Object


            mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

            mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);

            mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);

            // error Textview
            mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

            // ProgressBar reference
            mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);




    }



    // Show JsonData and hide the errors
    private void showJsonDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        // Then, make sure the JSON data is visible
        mSearchResultsTextView.setVisibility(View.VISIBLE);

    }

    //
    private void showErrorMessage() {

        mSearchResultsTextView.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search){
//            Context context = MainActivity.this;
//            String textToShow   = "Search cliked";
//            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();

            makeGithubSearchQuery();
            return true;

        }

        return super.onOptionsItemSelected(item);
    } // end onOptionsItemSelected()

    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        // Call getResponseFromHttpUrl and display the results in mSearchResultsTextView
        // Surround the call to getResponseFromHttpUrl with

//        String githubSearchResults = null;
//        try {
//            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubSearchUrl);
//            mSearchBoxEditText.setText(githubSearchResults);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        new GithubQueryTask().execute(githubSearchUrl);


    } // end makeGithubSearchQuery()


    public class GithubQueryTask extends AsyncTask<URL, Void, String > {


        //
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }



        // to perform the query. Return the result.
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String githubSearchResults = null;
            try {

                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            } catch (IOException e) {
                e.printStackTrace();

            }

            return githubSearchResults;
        } // end doInBackground()

        // to display the result in the TextView
        protected void onPostExecute(String githubSearchResults){

            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if(githubSearchResults != null && !githubSearchResults.equals("")){
                mSearchResultsTextView.setText(githubSearchResults);
                showJsonDataView();
            } else {
//                mSearchResultsTextView.setText("Result not Found");
                showErrorMessage();
//                String result = githubSearchResults;
                Log.println(Log.INFO, "Response-Result", "Rashid Saleem");

            }

        }

    } // end GitHubQueryTask()


}
