package com.vincenttetau.weatherapp;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.weather_condition_imageview)
    ImageView weatherConditionImageView;

    @BindView(R.id.time_textview)
    TextView timeTextView;

//    @BindView(R.id.condition_textview)
//    TextView conditionTextView;
//
//    @BindView(R.id.wind_direction_textview)
//    TextView windDirectionTextView;

    public WeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setTimeText(@Nullable String time) {
        timeTextView.setText(time);
    }

    public void setWeatherConditionImageResource (@DrawableRes int weatherConditionImageResource) {
        this.weatherConditionImageView.setImageResource(weatherConditionImageResource);
    }

    public void setConditionText(@Nullable String condition) {
//        conditionTextView.setText(condition);
    }

    public void setWindDirectionText(@StringRes int windDirectionResource) {
//        windDirectionTextView.setText(windDirectionResource);
    }

}
