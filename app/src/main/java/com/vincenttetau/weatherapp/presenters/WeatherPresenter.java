package com.vincenttetau.weatherapp.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vincenttetau.weatherapp.R;
import com.vincenttetau.weatherapp.models.Location;
import com.vincenttetau.weatherapp.networking.WeatherApi;
import com.vincenttetau.weatherapp.utils.StringUtil;
import com.vincenttetau.weatherapp.utils.TimeUtil;
import com.vincenttetau.weatherapp.models.Forecast;
import com.vincenttetau.weatherapp.models.Weather;
import com.vincenttetau.weatherapp.models.responses.CurrentWeatherResponse;
import com.vincenttetau.weatherapp.models.responses.ForecastResponse;
import com.vincenttetau.weatherapp.networking.NetworkingManager;
import com.vincenttetau.weatherapp.views.WeatherView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherPresenter extends BasePresenter<WeatherView> {

    private NetworkingManager networkingManager;

    private Disposable weatherDisposable;

    private List<Location> locations;

    private List<Forecast> forecasts;

    @Nullable
    private String currentError;

    public WeatherPresenter(NetworkingManager networkingManager) {
        this.networkingManager = networkingManager;
        this.forecasts = new ArrayList<>();
    }

    public void initialiseLocations(@NonNull String locationsJson) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Location>>() {}.getType();
        locations = gson.fromJson(locationsJson, listType);
    }

    public void bindView(@NonNull WeatherView weatherView) {
        super.bindView(weatherView);
        restoreState();
    }

    private void restoreState() {
        if (weatherDisposable != null) {
            getView().hideWeatherView();
            getView().setLoading(true);
        } else if (!forecasts.isEmpty()) {
            getView().showWeatherView();
            getView().setLoading(false);
            getView().updateList();
        }
        getView().setError(currentError);
    }

    private void loadWeather(final long cityId) {
        WeatherApi weatherApi = networkingManager.getWeatherApi();
        loadWeather(weatherApi.getCurrentWeather(cityId), weatherApi.getForecast(cityId));
    }

    public void loadWeather(@NonNull final String city) {
        WeatherApi weatherApi = networkingManager.getWeatherApi();
        loadWeather(weatherApi.getCurrentWeather(city), weatherApi.getForecast(city));
    }

    private void loadWeather(@NonNull Observable<CurrentWeatherResponse> currentWeatherObservable,
                            @NonNull final Observable<ForecastResponse> forecastObservable) {
        getView().hideWeatherView();
        getView().setLoading(true);
        currentError = null;
        getView().setError(currentError);

        if (weatherDisposable != null) {
            weatherDisposable.dispose();
            weatherDisposable = null;
        }

        forecasts.clear();

        currentWeatherObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentWeatherResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable disposable) {
                        weatherDisposable = disposable;
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull CurrentWeatherResponse currentWeatherResponse) {
                        handleWeatherLoadedSuccess(currentWeatherResponse, forecastObservable);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        getView().setError(R.string.generic_error_message);
                        getView().setLoading(false);
                        weatherDisposable = null;
                    }

                    @Override
                    public void onComplete() {
                        weatherDisposable = null;
                    }
                });
    }

    private void handleWeatherLoadedSuccess(@NonNull CurrentWeatherResponse currentWeatherResponse,
                                            @NonNull Observable<ForecastResponse> forecastObservable) {
        forecasts.add(currentWeatherResponse);
        loadForecast(forecastObservable);
    }

    private void loadForecast(@NonNull Observable<ForecastResponse> forecastObservable) {
        forecastObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForecastResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable disposable) {
                        weatherDisposable = disposable;
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ForecastResponse forecastResponse) {
                        handleForecastLoadedSuccess(forecastResponse);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        getView().setError(R.string.generic_error_message);
                        getView().setLoading(false);
                        weatherDisposable = null;
                    }

                    @Override
                    public void onComplete() {
                        weatherDisposable = null;
                    }
                });
    }

    private void handleForecastLoadedSuccess(@NonNull ForecastResponse forecastResponse) {
        if (forecastResponse.isSuccessful()) {
            getView().showWeatherView();
            forecasts.addAll(forecastResponse.getForecasts());
            getView().updateList();
            onPositionChanged(0);
            currentError = null;
        } else {
            getView().hideWeatherView();
            currentError = forecastResponse.getMessage();
        }
        getView().setError(currentError);
        getView().setLoading(false);
    }

    public void onLocationTextChanged(@Nullable String location) {
        if (location != null && location.length() >= 2) {
            getView().showLocationSuggestions(location);
        } else {
            getView().hideLocationSuggestions();
        }
    }

    public void onLocationSuggestionClicked(@NonNull Location location) {
        loadWeather(location.getId());
    }

    public void onPositionChanged(int position) {
        if (!forecasts.isEmpty()) {
            Forecast forecast = forecasts.get(position);
            getView().setTemperature(forecast.getTemperatureInfo());
            getView().setWindDirection(forecast.getWindInfo());
            getView().setDate(TimeUtil.formatDate(forecast.getDate()));
            getView().setTime(TimeUtil.formatTime(forecast.getDate()));

            Weather weather = forecast.getWeatherList().get(0);
            String weatherCondition = StringUtil.capitiliseFirstCharacter(weather.getDescription());
            getView().setWeatherCondition(weatherCondition);

            getView().updateBackground(getHourOfDay(forecast));
        }
    }

    private int getHourOfDay(@NonNull Forecast forecast) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(forecast.getDate());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    @Override
    public void onDestroy() {
        if (weatherDisposable != null) {
            weatherDisposable.dispose();
        }
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
