package com.vincenttetau.weatherapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Forecast {

    @SerializedName("dt")
    private Date date;

    private Main main;

    private Weather weather;

    private WindInfo windInfo;

    public Forecast(Date date, Main main, Weather weather, WindInfo windInfo) {
        this.date = date;
        this.main = main;
        this.weather = weather;
        this.windInfo = windInfo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public WindInfo getWindInfo() {
        return windInfo;
    }

    public void setWindInfo(WindInfo windInfo) {
        this.windInfo = windInfo;
    }
}
