package com.vincenttetau.weatherapp.models;

import android.support.annotation.NonNull;

public class Location {

    private long id;

    @NonNull
    private String name;

    @NonNull
    private String country;

    public Location(long id, @NonNull String name, @NonNull String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return name;
    }
}
