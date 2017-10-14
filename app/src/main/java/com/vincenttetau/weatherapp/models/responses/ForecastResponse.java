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

    public ForecastResponse(@NonNull String code, @NonNull String message) {
        super(code, message);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
