package com.vincenttetau.weatherapp;

import android.app.Application;

import com.vincenttetau.weatherapp.networking.NetworkingManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class WeatherApplication extends Application {

    private static final String DEFAULT_FONT_PATH = "fonts/Montserrat-Regular.ttf";

    private NetworkingManager networkingManager;

    @Override
    public void onCreate() {
        super.onCreate();

        this.networkingManager = new NetworkingManager();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(DEFAULT_FONT_PATH)
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public NetworkingManager getNetworkingManager() {
        return networkingManager;
    }
}
