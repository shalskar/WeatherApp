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

}
