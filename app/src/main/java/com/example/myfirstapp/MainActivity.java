package com.example.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myfirstapp.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
              implements HeadlinesFragment.OnListFragmentInteractionListener  {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_article);

        // Check the the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being resored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.

            if(savedInstanceState != null){
                return ;
            }

            // Create a new Fragment to be placed in the activity layout
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // In case this activity was started with spacial from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments((getIntent().getExtras()));

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        } // end if checking fragment_container

    }



    // Called when the user taps the Send button
    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }



    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
