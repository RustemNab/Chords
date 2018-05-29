package com.example.chords.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.chords.R;

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
    }
}
