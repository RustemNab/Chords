package com.example.chords.api;

import com.example.chords.api.interfaces.AuthApi;
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
    private static String AUTH_HEADER = "x-auth-token";
    private static String BASE_DOMAIN = "";

    private static AuthApi authApi;
    private static SongApi songApi;

    public static SongApi getSongApi() {
        if (songApi == null)
            songApi = getApiInterface(SongApi.class, BASE_DOMAIN, false);
        return songApi;
    }


    //Аутентификация
    public static AuthApi getAuthApi(){
        if(authApi == null)
            authApi = getApiInterface(AuthApi.class, BASE_DOMAIN, false);

        return  authApi;
    }

    private static<T> T getApiInterface(Class<T> tClass, String domain, boolean needTooken) {
        Retrofit.Builder rBuilder = new Retrofit.Builder()
                .baseUrl(domain)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        //Создаём клиент-билдера
        OkHttpClient.Builder clBuilder = new OkHttpClient.Builder();

        //Если нужно отправить токен
        if(needTooken)
            //Вешаем перехватчика на клиент-билдера
            clBuilder.addInterceptor(getAuthInterceptor());

        //Получаем клиента из клиент-билдера
        OkHttpClient client = clBuilder.build();

        //Вешаем клиента в ретрофит-билдер
        rBuilder.client(client);

        return rBuilder.build().create(tClass);
    }

    private static Interceptor getAuthInterceptor() {
        //Возвращает перехватчик
        return new AuthInterceptor();
    }


    private static class AuthInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //Старый запрос
            Request request = chain.request();
            //Новый запрос
            Request newRequest;

            //Добавим в хедер новому запросу (x-auth-token, значение токена)
            newRequest = request.newBuilder()
                    .addHeader(AUTH_HEADER, UserPreference.getInstance().getToken())
                    .build();

            return  chain.proceed(newRequest);
        }
    }
}
