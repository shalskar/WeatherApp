package com.vincenttetau.weatherapp.networking;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vincenttetau.weatherapp.utils.UnixTimeAdapter;

import java.io.IOException;
import java.util.Date;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkingManager {

    private static final String BASE_WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/";

    private static final String API_KEY_VALUE = "58ee8f0848f80e1ad674e77542ab9ea6";
    private static final String API_KEY_KEY = "APPID";
    private static final String UNIT_KEY = "units";
    private static final String UNIT_VALUE = "metric";

    private WeatherApi weatherApi;

    public NetworkingManager() {
        initialiseRetrofit();
    }

    private void initialiseRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_WEATHER_API_URL)
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create(createCustomGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApi.class);
    }

    @NonNull
    private Gson createCustomGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new UnixTimeAdapter())
                .create();
    }

    @NonNull
    private OkHttpClient createHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(API_KEY_KEY, API_KEY_VALUE)
                        .addQueryParameter(UNIT_KEY, UNIT_VALUE)
                        .build();

                Request.Builder requestBuilder = original.newBuilder().url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClientBuilder.build();
    }

    public WeatherApi getWeatherApi() {
        return weatherApi;
    }
}
