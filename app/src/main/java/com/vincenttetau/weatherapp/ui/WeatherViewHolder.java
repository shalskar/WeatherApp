package com.vincenttetau.weatherapp.ui;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vincenttetau.weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.weather_condition_imageview)
    ImageView weatherConditionImageView;

    @BindView(R.id.time_textview)
    TextView timeTextView;

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

}
