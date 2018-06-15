package com.example.chords.preference;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.chords.App;
import com.example.chords.model.ListSongResponse;
import com.example.chords.preference.interfaces.UserPreferenceName;

import java.util.Map;


public class UserPreference implements UserPreferenceName {

    private static final String USER_PREFERENCES = "user_preferences";

    private static UserPreference instance;

    //Параметры, кторые сохраняем (из Интерфейса)
    private ListSongResponse listSongResponse;
    private String chord;

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

    /**
     * Инициализация (если я не накосячил)
     * Создается мапа со всеми параметрами из preferences
     * забирается от туда listSongResponse
     * Пишу через мапу, так как как у него не стандартный тип (не инт, лонг и т.д.)
     *
     * chord забирается стандартным образом
     */
    private void initFields() {

        Map<String, ?> map = preferences.getAll();

        listSongResponse = (ListSongResponse) map.get(LISTRECORD);
        chord = preferences.getString(CHORD, "chord");
    }

    // public ListSongResponse getListSongResponse() {
    // return listSongResponse;
    //}


    public ListSongResponse getListSongResponse() {
        return listSongResponse;
    }

    public void setListSongResponse(ListSongResponse listSongResponse) {
        this.listSongResponse = listSongResponse;
    }

    public String getChord() {
        return chord;
    }

    public void setChord(String chord) {
        this.chord = chord;
    }
}
