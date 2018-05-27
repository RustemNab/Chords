package com.example.chords.api.interfaces;

import com.example.chords.model.ListSongResponse;
import com.example.chords.model.SongRequest;
import com.example.chords.model.SongResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface SongApi {

    @POST("EC2Co-EcsEl-14G7P3W3Z9F63-465517196.us-east-1.elb.amazonaws.com:5000")
    Observable<ListSongResponse> getSong(@Body SongRequest request);



}
