package com.example.chords.api.interfaces;

import com.example.chords.model.AuthRequest;
import com.example.chords.model.AuthResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Рустем on 28.04.2018.
 */

public interface AuthApi {
    @POST("/authenticate")
    Observable<AuthResponse> authUser (@Body AuthRequest requset);

}
