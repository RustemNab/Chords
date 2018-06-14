package com.example.chords.preference;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.chords.App;
import com.example.chords.model.ListSongResponse;
import com.example.chords.preference.interfaces.UserPreferenceName;


public class UserPreference implements UserPreferenceName {

    private static final String USER_PREFERENCES = "user_preferences";

    private static UserPreference instance;


    private ListSongResponse listSongResponse;

    private SharedPreferences preferences;

    public static UserPreference getInstance() {
        if (instance == null)
            instance = new UserPreference();

        return instance;
    }

    private UserPreference() {
        preferences = App.get().getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        initFields();
    }

    // public ListSongResponse getListSongResponse() {
    // return listSongResponse;
    //}

    public void setListSongResponse(ListSongResponse listSongResponse) {
        this.listSongResponse = listSongResponse;
    }

    private void initFields() {
        //  listSongResponse = preferences.getString(CHORD, "chord");
    }

}
