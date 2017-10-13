package com.vincenttetau.weatherapp;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("h:mm a");

    public static String formatDate(@NonNull Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String formatTime(@NonNull Date date) {
        return TIME_FORMAT.format(date);
    }

}
