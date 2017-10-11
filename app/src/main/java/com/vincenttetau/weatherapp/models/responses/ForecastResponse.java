package com.vincenttetau.weatherapp.models.responses;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.vincenttetau.weatherapp.models.Forecast;

import java.util.List;

public class ForecastResponse extends BaseResponse {

    @SerializedName("cnt")
    private int count;

    @SerializedName("list")
    private List<Forecast> forecasts;

    public ForecastResponse(@NonNull String code, float message) {
        super(code, message);
    }

}
