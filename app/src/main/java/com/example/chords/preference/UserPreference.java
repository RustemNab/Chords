package com.example.chords.preference;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.chords.App;
import com.example.chords.preference.interfaces.UserPreferenceName;

import static com.example.chords.preference.interfaces.UserPreferenceName.TOKEN;



public class UserPreference implements UserPreferenceName {

    private static final String USER_PREFERENCES = "user_preferences";

    private static UserPreference instance;

    private String token;

    private SharedPreferences preferences;

    public static UserPreference getInstance(){
        if (instance == null)
            instance = new UserPreference();

        return instance;
    }

    private UserPreference(){
        preferences = App.get().getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        initFields();
    }

    private void initFields() {
        token = preferences.getString(TOKEN, "TOKEN");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
