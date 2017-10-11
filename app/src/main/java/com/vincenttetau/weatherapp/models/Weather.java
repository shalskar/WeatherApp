package com.vincenttetau.weatherapp.models;

import android.support.annotation.NonNull;

public class Weather {

    private int id;

    @NonNull
    private String main;

    @NonNull
    private String description;

    public Weather(int id, @NonNull String main, @NonNull String description) {
        this.id = id;
        this.main = main;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getMain() {
        return main;
    }

    public void setMain(@NonNull String main) {
        this.main = main;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }
}
