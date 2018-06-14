package com.example.chords.listeners;


public interface OnDatabaseChangedListener {
    void onNewDatabaseEntryAdded();

    void onDatabaseEntryRenamed();
}