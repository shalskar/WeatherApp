package com.vincenttetau.weatherapp.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

public class UnixTimeAdapter extends TypeAdapter<Date> {

    @Override
    public void write(@NonNull JsonWriter out, @Nullable Date value) throws IOException {
        if (value == null)
            out.nullValue();
        else
            out.value(value.getTime() / 1000);
    }

    @Override
    public Date read(@Nullable JsonReader in) throws IOException {
        if (in != null)
            return new Date(in.nextLong() * 1000);
        else
            return null;
    }
}
