package com.vincenttetau.weatherapp.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.vincenttetau.weatherapp.models.TemperatureInfo;
import com.vincenttetau.weatherapp.models.WindInfo;

public interface WeatherView extends BaseView {

    void showLocationSuggestions(@NonNull String filterText);

    void hideLocationSuggestions();

    void showWeatherView();

    void hideWeatherView();

    void setLoading(boolean loading);

    void setError(@Nullable String errorMessage);

    void setError(@StringRes int errorMessageRes);

    void setTemperature(@NonNull TemperatureInfo temperatureInfo);

    void setWindDirection(@NonNull WindInfo windInfo);

    void setWeatherCondition(@NonNull String weatherCondition);

    void setDate(@NonNull String date);

    void setTime(@NonNull String time);

    void updateBackground(int hourOfDay);

    void updateList();

}