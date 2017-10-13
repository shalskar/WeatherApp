package com.vincenttetau.weatherapp.models.responses;

import com.vincenttetau.weatherapp.models.Forecast;
import com.vincenttetau.weatherapp.models.TemperatureInfo;
import com.vincenttetau.weatherapp.models.Weather;
import com.vincenttetau.weatherapp.models.WindInfo;

import java.util.Date;
import java.util.List;

public class CurrentWeatherResponse extends Forecast {

    public CurrentWeatherResponse(Date date, TemperatureInfo temperatureInfo, List<Weather> weatherList, WindInfo windInfo) {
        super(date, temperatureInfo, weatherList, windInfo);
    }

//    @SerializedName("coord")
//    private Coordinate coordinate;
//
//    @SerializedName("weather")
//    private List<Weather> weatherList;
//
//    private TemperatureInfo main;
//
//    public CurrentWeatherResponse(@NonNull String code, float message, Coordinate coordinate, List<Weather> weatherList, TemperatureInfo main) {
//        super(code, message);
//        this.coordinate = coordinate;
//        this.weatherList = weatherList;
//        this.main = main;
//    }
//
//    public Coordinate getCoordinate() {
//        return coordinate;
//    }
//
//    public void setCoordinate(Coordinate coordinate) {
//        this.coordinate = coordinate;
//    }
//
//    public List<Weather> getWeatherList() {
//        return weatherList;
//    }
//
//    public void setWeatherList(List<Weather> weatherList) {
//        this.weatherList = weatherList;
//    }
//
//    public TemperatureInfo getTemperatureInfo() {
//        return main;
//    }
//
//    public void setTemperatureInfo(TemperatureInfo main) {
//        this.main = main;
//    }

    // Todo continue fields

}
