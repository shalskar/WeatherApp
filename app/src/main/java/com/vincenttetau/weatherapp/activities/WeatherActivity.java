package com.vincenttetau.weatherapp.activities;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vincenttetau.weatherapp.models.Location;
import com.vincenttetau.weatherapp.ui.BufferItemDecoration;
import com.vincenttetau.weatherapp.ui.LocationSuggestionsAdapter;
import com.vincenttetau.weatherapp.utils.ColourUtil;
import com.vincenttetau.weatherapp.ui.FadingTextView;
import com.vincenttetau.weatherapp.utils.FileUtil;
import com.vincenttetau.weatherapp.utils.KeyboardUtil;
import com.vincenttetau.weatherapp.ui.OnScrollListenerAdapter;
import com.vincenttetau.weatherapp.R;
import com.vincenttetau.weatherapp.ui.WeatherAdapter;
import com.vincenttetau.weatherapp.WeatherApplication;
import com.vincenttetau.weatherapp.utils.ViewUtil;
import com.vincenttetau.weatherapp.utils.WeatherUtil;
import com.vincenttetau.weatherapp.models.Forecast;
import com.vincenttetau.weatherapp.models.TemperatureInfo;
import com.vincenttetau.weatherapp.models.WindInfo;
import com.vincenttetau.weatherapp.presenters.WeatherPresenter;
import com.vincenttetau.weatherapp.views.WeatherView;

import java.io.IOException;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class WeatherActivity extends BaseActivity<WeatherPresenter> implements WeatherView, WeatherAdapter.WeatherCallback {

    private static final char CHAR_DEGREE = (char) 0x00B0;

    private WeatherAdapter weatherAdapter;

    private ListPopupWindow locationSuggestionsPopUpWindow;

    private LocationSuggestionsAdapter locationSuggestionsArrayAdapter;

    private boolean isLocationInputActive;

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

    @BindColor(R.color.gradient_day_top)
    int gradientDayTopColor;

    @BindColor(R.color.gradient_day_bottom)
    int gradientDayBottomColor;

    @BindColor(R.color.gradient_night_top)
    int gradientNightTopColor;

    @BindColor(R.color.gradient_night_bottom)
    int gradientNightBottomColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));
        }

        ButterKnife.bind(this);

        initialiseRecyclerView();
        initialiseLocations();
        initialiseSuggestionsPopUpWindow();
        getPresenter().bindView(this);

        // This is to prevent onTextChanged() events when restoring the activity
        isLocationInputActive = savedInstanceState == null;
    }

    private void initialiseLocations() {
        try {
            getPresenter().initialiseLocations(FileUtil.openRawResource(this, R.raw.locations));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialiseSuggestionsPopUpWindow() {
        locationSuggestionsArrayAdapter = new LocationSuggestionsAdapter(this, getPresenter().getLocations());
        locationSuggestionsPopUpWindow = new ListPopupWindow(this);
        locationSuggestionsPopUpWindow.setAdapter(locationSuggestionsArrayAdapter);
        locationSuggestionsPopUpWindow.setAnchorView(locationEditText);
        locationSuggestionsPopUpWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onLocationSuggestionClicked(locationSuggestionsArrayAdapter.getItem(position));
            }
        });
    }

    private void initialiseRecyclerView() {
        weatherAdapter = new WeatherAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(weatherAdapter);
        recyclerView.addItemDecoration(new BufferItemDecoration((int) getResources().getDimension(R.dimen.weather_viewholder_width) / 2));

        new LinearSnapHelper().attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new OnScrollListenerAdapter(layoutManager) {
            @Override
            public void onScrolled(int centreMostPosition) {
                getPresenter().onPositionChanged(centreMostPosition);
            }
        });
    }

    private void onLocationSuggestionClicked(@NonNull Location location) {
        isLocationInputActive = false;
        locationEditText.setText(location.getName());
        isLocationInputActive = true;
        KeyboardUtil.hideKeyboard(locationEditText);
        locationEditText.clearFocus();
        locationSuggestionsPopUpWindow.dismiss();

        getPresenter().onLocationSuggestionClicked(location);
    }

    @Override
    protected WeatherPresenter createPresenter() {
        return new WeatherPresenter(((WeatherApplication) getApplication()).getNetworkingManager());
    }

    @OnTextChanged(R.id.location_edittext)
    void onLocationChanged() {
        if (!isLocationInputActive) return;
        getPresenter().onLocationTextChanged(locationEditText.getText().toString());
    }

    @OnEditorAction(R.id.location_edittext)
    boolean onLocationEntered() {
        KeyboardUtil.hideKeyboard(locationEditText);
        hideLocationSuggestions();
        locationEditText.clearFocus();
        containerViewGroup.findFocus();
        getPresenter().loadWeather(locationEditText.getText().toString());
        return true;
    }

    @Override
    public void showLocationSuggestions(@NonNull String filterText) {
        locationSuggestionsArrayAdapter.getFilter().filter(filterText);
        locationSuggestionsPopUpWindow.show();
    }

    @Override
    public void hideLocationSuggestions() {
        locationSuggestionsPopUpWindow.dismiss();
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
    }

    @Override
    public void updateBackground(int hourOfDay) {
        GradientDrawable gradientDrawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.background_gradient);

        float ratio = calculateGradientRatio(hourOfDay);

        final int topColour = ColourUtil.mixColours(gradientDayTopColor, gradientNightTopColor, ratio);
        int bottomColour = ColourUtil.mixColours(gradientDayBottomColor, gradientNightBottomColor, ratio);

        gradientDrawable.setColors(new int[]{bottomColour, topColour});

        ViewUtil.fadeInDrawable(containerViewGroup, gradientDrawable);
    }

    private float calculateGradientRatio(int hourOfDay) {
        float ratio;
        if (hourOfDay <= 12) {
            ratio = hourOfDay / 12f;
        } else {
            ratio = (12 - (hourOfDay - 12)) / 12f;
        }

        return new AccelerateDecelerateInterpolator().getInterpolation(ratio);
    }

    @Override
    public List<Forecast> getForecasts() {
        return getPresenter().getForecasts();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isLocationInputActive = true;
    }

    @Override
    protected void onPause() {
        locationSuggestionsPopUpWindow.dismiss();
        super.onPause();
    }
}
