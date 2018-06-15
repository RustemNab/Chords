package com.example.chords.api.interfaces;

import com.example.chords.model.ListSongResponse;
import com.example.chords.model.SongRequest;
import com.example.chords.model.SongResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface SongApi {
    @Multipart
    @POST("/")
    Observable<ListSongResponse> getSong(@Part("description") RequestBody description,
                                   @Part MultipartBody.Part file);

}