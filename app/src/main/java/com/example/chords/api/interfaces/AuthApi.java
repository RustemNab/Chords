package com.example.chords.api.interfaces;

import com.example.chords.model.AuthRequest;
import com.example.chords.model.AuthResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface AuthApi {
    @POST("/auth")
    Observable<AuthResponse> authUser (@Body AuthRequest requset);

}
