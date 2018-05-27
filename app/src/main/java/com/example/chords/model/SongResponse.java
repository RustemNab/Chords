package com.example.chords.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongResponse {
    @SerializedName("chord")
    @Expose
    private String chord;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("start")
    @Expose
    private String start;

    public String getChord() {
        return chord;
    }

    public void setChord(String chord) {
        this.chord = chord;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
