package com.vincenttetau.weatherapp;

import android.app.Application;

import com.vincenttetau.weatherapp.networking.NetworkingManager;

public class WeatherApplication extends Application {

    private NetworkingManager networkingManager;

    @Override
    public void onCreate() {
        super.onCreate();

        this.networkingManager = new NetworkingManager(this);
    }

    public NetworkingManager getNetworkingManager() {
        return networkingManager;
    }
}
