package com.vincenttetau.weatherapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vincenttetau.weatherapp.R;
import com.vincenttetau.weatherapp.models.Forecast;
import com.vincenttetau.weatherapp.models.Weather;
import com.vincenttetau.weatherapp.utils.TimeUtil;
import com.vincenttetau.weatherapp.utils.WeatherUtil;

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
        Forecast forecast = weatherCallback.getForecasts().get(position);
        Weather weather = forecast.getWeatherList().get(0);

        viewHolder.setWeatherConditionImageResource(WeatherUtil.getWeatherConditionImageResource(weather.getId()));
        viewHolder.setTimeText(TimeUtil.formatTime(forecast.getDate()));
    }

    @Override
    public int getItemCount() {
        return weatherCallback.getForecasts().size();
    }

    public interface WeatherCallback {

        List<Forecast> getForecasts();

    }
}
