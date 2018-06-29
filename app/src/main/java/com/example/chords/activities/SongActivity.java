package com.example.chords.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chords.R;
import com.example.chords.model.SongResponse;
import com.example.chords.preference.UserPreference;

import java.util.List;
import java.util.Map;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

//        ListView listChords = (ListView) findViewById(R.id.listChords);
//
//
//        // создаем адаптер
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, UserPreference.getInstance().getListSongResponse());
//
//        // присваиваем адаптер списку
//        listChords.setAdapter(adapter);
    }
}
