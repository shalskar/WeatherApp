package com.vincenttetau.weatherapp.networking;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;

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

    private static final String API_KEY = "58ee8f0848f80e1ad674e77542ab9ea6";

    private Context context;

    private Retrofit retrofit;

    private WeatherApi weatherApi;

    public NetworkingManager(Context context) {
        this.context = context;

        initialiseRetrofit();
    }

    private void initialiseRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_WEATHER_API_URL)
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApi.class);
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
                        .addQueryParameter("APPID", API_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder().url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClientBuilder.build();
    }

    // TODO possibly make the networking manager cache results
    public WeatherApi getWeatherApi() {
        return weatherApi;
    }
}
