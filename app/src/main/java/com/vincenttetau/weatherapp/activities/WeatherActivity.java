package com.vincenttetau.weatherapp.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vincenttetau.weatherapp.OnScrollStopListener;
import com.vincenttetau.weatherapp.R;
import com.vincenttetau.weatherapp.WeatherAdapter;
import com.vincenttetau.weatherapp.WeatherApplication;
import com.vincenttetau.weatherapp.WeatherUtil;
import com.vincenttetau.weatherapp.models.Forecast;
import com.vincenttetau.weatherapp.models.TemperatureInfo;
import com.vincenttetau.weatherapp.models.WindInfo;
import com.vincenttetau.weatherapp.presenters.WeatherPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WeatherActivity extends AppCompatActivity implements WeatherPresenter.WeatherView, WeatherAdapter.WeatherCallback {

    private WeatherPresenter weatherPresenter;

    private WeatherAdapter weatherAdapter;

    private LinearLayoutManager layoutManager;

    private SnapHelper snapHelper;

    private OnScrollStopListener onScrollListener;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.temperature_textview)
    TextView temperatureTextView;

    @BindView(R.id.weather_condition_textview)
    TextView weatherConditionTextView;

    @BindView(R.id.min_temperature_textview)
    TextView minTemperatureTextView;

    @BindView(R.id.max_temperature_textview)
    TextView maxTemperatureTextView;

    @BindView(R.id.humidity_textview)
    TextView humidityTextView;

    @BindView(R.id.wind_direction_textview)
    TextView windDirectionTextView;

    @BindView(R.id.date_textview)
    TextView dateTextView;

    @BindView(R.id.time_textview)
    TextView timeTextView;

    @BindView(R.id.zoom_seekbar)
    SeekBar zoomSeekBar;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);

        initialisePresenter(savedInstanceState == null);
        initialiseRecyclerView();
        initialiseSeekBar();
    }

    private void initialiseRecyclerView() {
        weatherAdapter = new WeatherAdapter(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(weatherAdapter);
        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        onScrollListener = new OnScrollStopListener(layoutManager) {
            @Override
            public void onScrollStopped(int centreMostPosition) {
                weatherPresenter.onPositionChanged(centreMostPosition);
                zoomSeekBar.setProgress(centreMostPosition);
            }
        };

        recyclerView.addOnScrollListener(onScrollListener);
    }

    private void initialiseSeekBar() {
        zoomSeekBar.setMax(getWeatherData().size());
        zoomSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private void initialisePresenter(boolean firstLoad) {
        if (firstLoad) {
            weatherPresenter = new WeatherPresenter(((WeatherApplication) getApplication()).getNetworkingManager());
        } else {
            weatherPresenter = (WeatherPresenter) getLastCustomNonConfigurationInstance();
        }
        weatherPresenter.bindView(this);
    }

    @Override
    public void setTemperature(@NonNull TemperatureInfo temperatureInfo) {
        // todo clean up
        temperatureTextView.setText(String.format("%.0f", temperatureInfo.getTemperature()) + (char) 0x00B0 + "C");
        minTemperatureTextView.setText(String.format("%.0f", temperatureInfo.getMinTemperature()) + (char) 0x00B0 + "C");
        maxTemperatureTextView.setText(String.format("%.0f", temperatureInfo.getMaxTemperature()) + (char) 0x00B0 + "C");
        humidityTextView.setText(String.format("%.0f", temperatureInfo.getHumidity()) + "%");
    }

    @Override
    public void setWindDirection(@NonNull WindInfo windInfo) {
        windDirectionTextView.setText(WeatherUtil.getWindDirectionStringResource(windInfo.getDegree()));
    }

    @Override
    public void setWeatherCondition(@NonNull String weatherCondition) {
        weatherConditionTextView.setText(weatherCondition);
    }

    @Override
    public void setDate(@NonNull String date) {
        dateTextView.setText(date);
    }

    @Override
    public void setTime(@NonNull String time) {
        timeTextView.setText(time);
    }

    @Override
    public void updateList() {
        weatherAdapter.notifyDataSetChanged();
        zoomSeekBar.post(new Runnable() {
            @Override
            public void run() {
                zoomSeekBar.setMax(getWeatherData().size());
            }
        });
    }

    @Override
    public List<Forecast> getWeatherData() {
        return weatherPresenter.getWeatherData();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return weatherPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherPresenter.unbindView();
    }

    @Override
    protected void attachBaseContext(@NonNull Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int position, boolean fromUser) {
            if (fromUser) {
                weatherPresenter.onPositionChanged(position);
                recyclerView.smoothScrollToPosition(position);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            onScrollListener.setActive(false);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            onScrollListener.setActive(true);
        }
    };

}
