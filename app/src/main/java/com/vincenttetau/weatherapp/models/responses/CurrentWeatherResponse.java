package com.vincenttetau.weatherapp.models.responses;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.vincenttetau.weatherapp.models.Coordinate;
import com.vincenttetau.weatherapp.models.Weather;

import java.util.List;

/**
 * Created by RachelTeTau on 11/10/17.
 */

public class CurrentWeatherResponse extends BaseResponse {

    @SerializedName("coord")
    private Coordinate coordinate;

    @SerializedName("weather")
    private List<Weather> weatherList;

    public CurrentWeatherResponse(@NonNull String code, float message, @NonNull Coordinate coordinate, @NonNull List<Weather> weatherList) {
        super(code, message);
        this.coordinate = coordinate;
        this.weatherList = weatherList;
    }

    // Todo continue fields

}
