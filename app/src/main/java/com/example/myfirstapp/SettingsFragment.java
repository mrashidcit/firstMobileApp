package com.example.myfirstapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import java.util.List;

/**
 * Created by Rashid Saleem on 10/18/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat
                                implements SharedPreferences.OnSharedPreferenceChangeListener{
    // COMPLETED (2) Create a class called SettingsFragment that extends PreferenceFragmentCompat

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // COMPLETED (5) In SettingsFragment's onCreatePreferences method add the preference file using the
        // addPreferencesFromResource method

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);

        // COMPLETED (3) Get the preference screen, get the number of preferences and iterate through
        // all of the preferences if it is not a checkbox preference, call the setSummary method
        // passing in a preference and the value of the preference
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();

        // Go through all of the preferences, and set up their preference summary.
        for (int i = 0; i < count; i ++) {
            Preference preference = prefScreen.getPreference(i);
            // You don't need to set up preference summaries for checkbox preferences because
            // they are already set up in xml using summaryOff and summary On
            if(!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);

            }

        } // end for loop

    } // end onCreatePreferences


    // COMPLETED (4) Override onSharedPreferenceChanged and, if it is not a checkbox preference,
    // call setPreferenceSummary on the changed preference
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Firgure out which preference was changed
        Preference preference = findPreference(key);
        if (null != preference) {
            // Updates the summary for the preference
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }



    // COMPLETED (2) Create a setPreferenceSummary which takes a Preference and String value as parameters.
    // This method should check if the preference is a ListPreference and, if so, find the label
    // associated with the value. You can do this by using the findIndexOfValue and getEntries methods
    // of Preference.
    /**
     * Updates the summary for the preference
     *
     * @param preference The preference to be updated
     * @param value      The value that the preference was updated to
     */
    private void setPreferenceSummary(Preference preference, String value){
        if (preference instanceof ListPreference) {
            // For list preferences, firgue out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (preference instanceof EditTextPreference) {
            // For EditTextPreferences, set the summary to the value's simple string representation.
            preference.setSummary(value);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
