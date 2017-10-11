package com.vincenttetau.weatherapp.models;

import com.google.gson.annotations.SerializedName;

// Todo find more fitting name
public class Main {

    @SerializedName("temp")
    private float temperature;

    @SerializedName("temp_min")
    private float minTemperature;

    @SerializedName("temp_max")
    private float maxTemperature;

    public Main(float temperature, float minTemperature, float maxTemperature) {
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
}
