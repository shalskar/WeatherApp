package com.vincenttetau.weatherapp.presenters;

import android.support.annotation.NonNull;

import com.vincenttetau.weatherapp.models.responses.CurrentWeatherResponse;
import com.vincenttetau.weatherapp.networking.NetworkingManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter {

    private WeatherView weatherView;

    private NetworkingManager networkingManager;

    public WeatherPresenter(NetworkingManager networkingManager) {
        this.networkingManager = networkingManager;
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
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void unbindView() {
        this.weatherView = null;
    }

    public interface WeatherView {

    }
}
