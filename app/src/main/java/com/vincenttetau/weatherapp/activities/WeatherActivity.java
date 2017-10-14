package com.vincenttetau.weatherapp.activities;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vincenttetau.weatherapp.utils.ColourUtil;
import com.vincenttetau.weatherapp.FadingTextView;
import com.vincenttetau.weatherapp.utils.KeyboardUtil;
import com.vincenttetau.weatherapp.OnScrollListenerAdapter;
import com.vincenttetau.weatherapp.R;
import com.vincenttetau.weatherapp.WeatherAdapter;
import com.vincenttetau.weatherapp.WeatherApplication;
import com.vincenttetau.weatherapp.utils.WeatherUtil;
import com.vincenttetau.weatherapp.models.Forecast;
import com.vincenttetau.weatherapp.models.TemperatureInfo;
import com.vincenttetau.weatherapp.models.WindInfo;
import com.vincenttetau.weatherapp.presenters.WeatherPresenter;
import com.vincenttetau.weatherapp.views.WeatherView;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;

public class WeatherActivity extends BaseActivity<WeatherPresenter> implements WeatherView, WeatherAdapter.WeatherCallback {

    private static final char CHAR_DEGREE = (char) 0x00B0;

    private static final int FORECASTS_PER_DAY = 8;

    private WeatherAdapter weatherAdapter;

    private LinearLayoutManager layoutManager;

    private SnapHelper snapHelper;

    private OnScrollListenerAdapter onScrollListener;

    @BindView(R.id.container_viewgroup)
    ViewGroup containerViewGroup;

    @BindView(R.id.weather_viewgroup)
    ViewGroup weatherViewGroup;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.error_textview)
    TextView errorTextView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.location_edittext)
    EditText locationEditText;

    @BindView(R.id.temperature_textview)
    TextView temperatureTextView;

    @BindView(R.id.weather_condition_textview)
    FadingTextView weatherConditionTextView;

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

    @BindColor(R.color.gradient_day_top)
    int gradientDayTopColor;

    @BindColor(R.color.gradient_day_bottom)
    int gradientDayBottomColor;

    @BindColor(R.color.gradient_night_top)
    int gradientNightTopColor;

    @BindColor(R.color.gradient_night_bottom)
    int gradientNightBottomColor;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);

        initialiseRecyclerView();
        initialiseSeekBar();
        getPresenter().bindView(this);
    }

    private void initialiseRecyclerView() {
        weatherAdapter = new WeatherAdapter(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(weatherAdapter);
//        snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

        onScrollListener = new OnScrollListenerAdapter(layoutManager) {
            @Override
            public void onScrolled(int centreMostPosition) {
                getPresenter().onPositionChanged(centreMostPosition);
                setSeekBarProgress(centreMostPosition / FORECASTS_PER_DAY);
            }
        };

        recyclerView.addOnScrollListener(onScrollListener);
    }

    private void initialiseSeekBar() {
        zoomSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    @Override
    protected WeatherPresenter createPresenter() {
        return new WeatherPresenter(((WeatherApplication) getApplication()).getNetworkingManager());
    }

    @OnEditorAction(R.id.location_edittext)
    boolean onLocationEntered() {
        // todo fix virtual keyboard go not working
        // todo check why this being called twice
        KeyboardUtil.hideKeyboard(locationEditText);
        getPresenter().loadWeather(locationEditText.getText().toString());
        return true;
    }

    @Override
    public void showWeatherView() {
        weatherViewGroup.animate().alpha(1).start();
    }

    @Override
    public void hideWeatherView() {
        weatherViewGroup.animate().alpha(0).start();
    }

    @Override
    public void setLoading(boolean loading) {
        progressBar.animate().alpha(loading ? 1 : 0).start();
    }

    @Override
    public void setError(@Nullable String errorMessage) {
        errorTextView.setText(errorMessage);
    }

    @Override
    public void setError(@StringRes int errorMessageRes) {
        errorTextView.setText(errorMessageRes);
    }

    private void setSeekBarProgress(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            zoomSeekBar.setProgress(position, true);
        } else {
            zoomSeekBar.setProgress(position);
        }
    }

    @Override
    public void setTemperature(@NonNull TemperatureInfo temperatureInfo) {
        temperatureTextView.setText(getString(R.string.format_degree_celcius, temperatureInfo.getTemperature(), CHAR_DEGREE));
        minTemperatureTextView.setText(getString(R.string.format_degree_celcius, temperatureInfo.getMinTemperature(), CHAR_DEGREE));
        maxTemperatureTextView.setText(getString(R.string.format_degree_celcius, temperatureInfo.getMaxTemperature(), CHAR_DEGREE));
        humidityTextView.setText(String.format("%.0f", temperatureInfo.getHumidity()) + "%");
    }

    @Override
    public void setWindDirection(@NonNull WindInfo windInfo) {
        windDirectionTextView.setText(WeatherUtil.getWindDirectionStringResource(windInfo.getDegree()));
    }

    @Override
    public void setWeatherCondition(@NonNull final String weatherCondition) {
        weatherConditionTextView.setTextAnimated(weatherCondition);
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
        weatherViewGroup.setVisibility(View.VISIBLE);
        weatherAdapter.notifyDataSetChanged();
        zoomSeekBar.post(new Runnable() {
            @Override
            public void run() {
                zoomSeekBar.setMax((getForecasts().size() - 1) / FORECASTS_PER_DAY);
            }
        });
    }

    @Override
    public void updateBackground(int hours) {
        if (getForecasts().isEmpty()) {
            return;
        }

        GradientDrawable gradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.background_gradient);
        float ratio;

        if (hours <= 12) {
            ratio = hours / 12f;
        } else {
            ratio = (12 - (hours - 12)) / 12f;
        }

        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        ratio = interpolator.getInterpolation(ratio);

        int topColour = ColourUtil.mixColours(gradientDayTopColor, gradientNightTopColor, ratio);
        int bottomColour = ColourUtil.mixColours(gradientDayBottomColor, gradientNightBottomColor, ratio);

        gradientDrawable.setColors(new int[]{bottomColour, topColour});

        Drawable currentBackground = containerViewGroup.getBackground();

        if (currentBackground instanceof TransitionDrawable) {
            currentBackground = ((TransitionDrawable) currentBackground).getDrawable(1);
        }

        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{currentBackground, gradientDrawable});
        transitionDrawable.startTransition(100);

        containerViewGroup.setBackground(transitionDrawable);
    }

    @Override
    public List<Forecast> getForecasts() {
        return getPresenter().getForecasts();
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int position, boolean fromUser) {
            int actualPosition = position * FORECASTS_PER_DAY;
            if (fromUser) {
                getPresenter().onPositionChanged(actualPosition);
                recyclerView.smoothScrollToPosition(actualPosition);
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
