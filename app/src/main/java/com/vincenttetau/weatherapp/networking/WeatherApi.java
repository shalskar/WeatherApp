package com.vincenttetau.weatherapp.networking;

import com.vincenttetau.weatherapp.models.responses.CurrentWeatherResponse;
import com.vincenttetau.weatherapp.models.responses.ForecastResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather")
    Observable<CurrentWeatherResponse> getCurrentWeather(@Query("q") String city);

    @GET("forecast")
    Observable<ForecastResponse> getForecast(@Query("q") String city);

    @GET("weather")
    Observable<CurrentWeatherResponse> getCurrentWeather(@Query("id") long cityId);

    @GET("forecast")
    Observable<ForecastResponse> getForecast(@Query("id") long cityId);

}
