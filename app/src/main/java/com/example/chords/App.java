package com.example.chords;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.example.chords.preference.UserPreference;

/**
 * Created by Рустем on 28.04.2018.
 */

public class App extends Application {

    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        initPreferences();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private void initPreferences() {
        UserPreference.getInstance();
    }

    public static App get(){
        return context;
    }
}