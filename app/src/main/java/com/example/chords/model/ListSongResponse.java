package com.example.chords.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Рустем on 22.05.2018.
 */

public class ListSongResponse {
    @SerializedName("chords")
    @Expose
    List<SongResponse> song;


    public List<SongResponse> getPosts() {
        return song;
    }

    public void setSongS(List<SongResponse> song) {
        this.song = song;
    }
}
