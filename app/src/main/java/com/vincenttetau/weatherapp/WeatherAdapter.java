package com.vincenttetau.weatherapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vincenttetau.weatherapp.models.Forecast;
import com.vincenttetau.weatherapp.models.Weather;

import java.text.SimpleDateFormat;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

    private WeatherCallback weatherCallback;

    public WeatherAdapter(WeatherCallback weatherCallback) {
        this.weatherCallback = weatherCallback;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder viewHolder, int position) {
        Forecast forecast = weatherCallback.getWeatherData().get(position);
        Weather weather = forecast.getWeatherList().get(0);

        viewHolder.setWeatherConditionImageResource(WeatherUtil.getWeatherConditionImageResource(weather.getId()));
        viewHolder.setTimeText(TimeUtil.formatTime(forecast.getDate()));
        viewHolder.setConditionText(weather.getDescription());
        viewHolder.setWindDirectionText(WeatherUtil.getWindDirectionStringResource(forecast.getWindInfo().getDegree()));
    }

    @Override
    public int getItemCount() {
        return weatherCallback.getWeatherData().size();
    }

    public interface WeatherCallback {

        List<Forecast> getWeatherData();

    }
}
