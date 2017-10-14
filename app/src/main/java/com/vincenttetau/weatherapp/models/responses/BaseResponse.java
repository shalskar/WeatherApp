package com.vincenttetau.weatherapp.models.responses;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    // todo possibly don't need to check error codes
    private static final String SUCCESSFUL = "200";

    @SerializedName("cod")
    @NonNull
    private String code;

    @NonNull
    private String message;

    public BaseResponse(@NonNull String code, @NonNull String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isSuccessful() {
        return code.equals(SUCCESSFUL);
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }
}
