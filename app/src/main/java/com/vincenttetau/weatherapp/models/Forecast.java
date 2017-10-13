package com.vincenttetau.weatherapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Forecast {

    @SerializedName("dt")
    private Date date;

    @SerializedName("main")
    private TemperatureInfo temperatureInfo;

    @SerializedName("weather")
    private List<Weather> weatherList;

    @SerializedName("wind")
    private WindInfo windInfo;

    public Forecast(Date date, TemperatureInfo temperatureInfo, List<Weather> weatherList, WindInfo windInfo) {
        this.date = date;
        this.temperatureInfo = temperatureInfo;
        this.weatherList = weatherList;
        this.windInfo = windInfo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TemperatureInfo getTemperatureInfo() {
        return temperatureInfo;
    }

    public void setTemperatureInfo(TemperatureInfo temperatureInfo) {
        this.temperatureInfo = temperatureInfo;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public WindInfo getWindInfo() {
        return windInfo;
    }

    public void setWindInfo(WindInfo windInfo) {
        this.windInfo = windInfo;
    }
}
