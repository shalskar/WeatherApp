package com.vincenttetau.weatherapp.models.responses;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RachelTeTau on 11/10/17.
 */

public class BaseResponse {

    @SerializedName("cod")
    @NonNull
    private String code;

    private float message;

    public BaseResponse(@NonNull String code, float message) {
        this.code = code;
        this.message = message;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    public float getMessage() {
        return message;
    }

    public void setMessage(float message) {
        this.message = message;
    }
}
