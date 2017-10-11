package com.vincenttetau.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class WindInfo {

    private float speed;

    @SerializedName("deg")
    private float degree;

    public WindInfo(float speed, float degree) {
        this.speed = speed;
        this.degree = degree;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }
}
