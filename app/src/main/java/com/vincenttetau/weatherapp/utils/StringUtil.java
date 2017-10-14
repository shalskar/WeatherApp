package com.vincenttetau.weatherapp.utils;

import android.support.annotation.NonNull;

public class StringUtil {

    @NonNull
    public static String capitiliseFirstCharacter(@NonNull String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}
