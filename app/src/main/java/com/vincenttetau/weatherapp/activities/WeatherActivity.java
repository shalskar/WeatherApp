package com.vincenttetau.weatherapp.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.vincenttetau.weatherapp.R;
import com.vincenttetau.weatherapp.WeatherApplication;
import com.vincenttetau.weatherapp.models.responses.CurrentWeatherResponse;
import com.vincenttetau.weatherapp.networking.WeatherApi;
import com.vincenttetau.weatherapp.presenters.WeatherPresenter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherActivity extends AppCompatActivity implements WeatherPresenter.WeatherView {

    private WeatherPresenter weatherPresenter;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initialisePresenter(savedInstanceState == null);
    }

    private void initialisePresenter(boolean firstLoad) {
        if (firstLoad) {
            weatherPresenter = new WeatherPresenter(((WeatherApplication) getApplication()).getNetworkingManager());
        } else {
            weatherPresenter = (WeatherPresenter) getLastCustomNonConfigurationInstance();
        }
        weatherPresenter.bindView(this);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return weatherPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherPresenter.unbindView();
    }

}
