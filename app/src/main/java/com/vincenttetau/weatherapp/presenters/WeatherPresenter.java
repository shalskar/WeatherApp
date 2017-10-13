package com.vincenttetau.weatherapp.presenters;

import android.support.annotation.NonNull;

import com.vincenttetau.weatherapp.TimeUtil;
import com.vincenttetau.weatherapp.models.Forecast;
import com.vincenttetau.weatherapp.models.TemperatureInfo;
import com.vincenttetau.weatherapp.models.Weather;
import com.vincenttetau.weatherapp.models.WindInfo;
import com.vincenttetau.weatherapp.models.responses.CurrentWeatherResponse;
import com.vincenttetau.weatherapp.models.responses.ForecastResponse;
import com.vincenttetau.weatherapp.networking.NetworkingManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter {

    private WeatherView weatherView;

    private NetworkingManager networkingManager;

    private List<Forecast> weatherData;

    public WeatherPresenter(NetworkingManager networkingManager) {
        this.networkingManager = networkingManager;
        this.weatherData = new ArrayList<>();
    }

    public void bindView(@NonNull WeatherView weatherView) {
        this.weatherView = weatherView;
        loadWeather();
    }

    private void loadWeather() {
        networkingManager.getWeatherApi()
                .getCurrentWeather("Wellington")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentWeatherResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull CurrentWeatherResponse currentWeatherResponse) {
                        weatherData.add(currentWeatherResponse);
                        loadForecast();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadForecast() {
        networkingManager.getWeatherApi()
                .getForecast("Wellington")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForecastResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ForecastResponse forecastResponse) {
                        weatherData.addAll(forecastResponse.getForecasts());
                        weatherView.updateList();
                        onPositionChanged(0);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void onPositionChanged(int position) {
        Forecast forecast = weatherData.get(position);
        weatherView.setTemperature(forecast.getTemperatureInfo());
        weatherView.setWindDirection(forecast.getWindInfo());
        weatherView.setDate(TimeUtil.formatDate(forecast.getDate()));
        weatherView.setTime(TimeUtil.formatTime(forecast.getDate()));

        // todo move to util
        Weather weather = forecast.getWeatherList().get(0);
        String weatherCondition = weather.getDescription();
        weatherCondition = weatherCondition.substring(0, 1).toUpperCase() + weatherCondition.substring(1);
        weatherView.setWeatherCondition(weatherCondition);
    }

    public void unbindView() {
        this.weatherView = null;
    }

    public List<Forecast> getWeatherData() {
        return weatherData;
    }

    public interface WeatherView {

        void setTemperature(@NonNull TemperatureInfo temperatureInfo);

        void setWindDirection(@NonNull WindInfo windInfo);

        void setWeatherCondition(@NonNull String weatherCondition);

        void setDate(@NonNull String date);

        void setTime(@NonNull String time);

        void updateList();

    }
}
