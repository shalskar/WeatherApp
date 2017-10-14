package com.vincenttetau.weatherapp.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.vincenttetau.weatherapp.R;

public class WeatherUtil {

    public static final String NO_FLOATING_POINT_NUMBER_FORMAT = "%0.f";

    @StringRes
    public static int getWindDirectionStringResource(float windDirection) {
        if (windDirection >= 337.5f || windDirection < 22.5f) {
            return R.string.north;
        } else if (windDirection < 67.5f) {
            return R.string.northeast;
        } else if (windDirection < 112.5f) {
            return R.string.east;
        } else if (windDirection < 157.5f) {
            return R.string.southeast;
        } else if (windDirection < 202.5f) {
            return R.string.south;
        } else if (windDirection < 247.5f) {
            return R.string.southwest;
        } else if (windDirection < 292.5f) {
            return R.string.west;
        } else {
            return R.string.northwest;
        }
    }

    @DrawableRes
    public static int getWeatherConditionImageResource(int weatherConditionId) {
        if (weatherConditionId == 761 || weatherConditionId == 781 ||
                (weatherConditionId >= 200 && weatherConditionId <= 232)) {
            return R.drawable.ic_storm;
        } else if (weatherConditionId >= 300 && weatherConditionId <= 321) {
            return R.drawable.ic_light_rain;
        } else if ((weatherConditionId >= 500 && weatherConditionId <= 504) ||
                (weatherConditionId >= 520 && weatherConditionId <= 531)) {
            return R.drawable.ic_rain;
        } else if (weatherConditionId == 511 || (weatherConditionId >= 600 && weatherConditionId <= 622)) {
            return R.drawable.ic_snow;
        } else if (weatherConditionId >= 701 && weatherConditionId <= 761) {
            return R.drawable.ic_fog;
        } else if (weatherConditionId == 800) {
            return R.drawable.ic_sun;
        } else if (weatherConditionId == 801) {
            return  R.drawable.ic_light_clouds;
        } else if (weatherConditionId >= 802 && weatherConditionId <= 804) {
            return  R.drawable.ic_cloudy;
        } else {
            // TODO remove
            return R.drawable.ic_temperature;
        }
    }

}
