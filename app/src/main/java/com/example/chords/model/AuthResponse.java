package com.example.chords.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aydar on 12.04.2018.
 */

//Ответка от сервера
public class AuthResponse {
    @SerializedName("token")
    @Expose
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
