package com.example.chords.api;

import com.example.chords.api.interfaces.SongApi;
import com.example.chords.preference.UserPreference;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiModule {
    private static String BASE_DOMAIN = "http://35.228.252.81";

    private static SongApi songApi;

    public static SongApi getSongApi() {
        if (songApi == null)
            songApi = getApiInterface(SongApi.class, BASE_DOMAIN);
        return songApi;
    }


    private static <T> T getApiInterface(Class<T> tClass, String domain) {
        Retrofit.Builder rBuilder = new Retrofit.Builder()
                .baseUrl(domain)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        //Создаём клиент-билдера
        OkHttpClient.Builder clBuilder = new OkHttpClient.Builder();

        //Получаем клиента из клиент-билдера
        OkHttpClient client = clBuilder.build();

        //Вешаем клиента в ретрофит-билдер
        rBuilder.client(client);

        return rBuilder.build().create(tClass);
    }
}
