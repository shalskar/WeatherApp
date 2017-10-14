package com.vincenttetau.weatherapp;

import com.google.gson.Gson;
import com.vincenttetau.weatherapp.models.TemperatureInfo;
import com.vincenttetau.weatherapp.models.WindInfo;
import com.vincenttetau.weatherapp.models.responses.CurrentWeatherResponse;
import com.vincenttetau.weatherapp.models.responses.ForecastResponse;
import com.vincenttetau.weatherapp.networking.NetworkingManager;
import com.vincenttetau.weatherapp.networking.WeatherApi;
import com.vincenttetau.weatherapp.presenters.WeatherPresenter;
import com.vincenttetau.weatherapp.views.WeatherView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherTest extends BaseTest {

    private static final String CURRENT_WEATHER_RESPONSE_PATH = "current_weather_response.json";

    private static final String FORECAST_RESPONSE_PATH = "forecast_response.json";

    private static final String ERROR_RESPONSE_PATH = "error_response.json";

    @Mock
    private NetworkingManager networkingManager;

    @Mock
    private WeatherApi weatherApi;

    @Mock
    private WeatherView weatherView;

    private WeatherPresenter weatherPresenter;

    @Before
    @Override
    public void setup() {
        super.setup();

        weatherPresenter = new WeatherPresenter(networkingManager);
        weatherPresenter.bindView(weatherView);

        when(networkingManager.getWeatherApi()).thenReturn(weatherApi);
    }

    private void loadSuccessfulResponses() {
        Gson gson = createCustomGson();

        String currentWeatherResponseJson = convertStreamToString(getClass().getClassLoader().getResourceAsStream(CURRENT_WEATHER_RESPONSE_PATH));
        CurrentWeatherResponse currentWeatherResponse = gson.fromJson(currentWeatherResponseJson, CurrentWeatherResponse.class);

        String forecastResponseJson = convertStreamToString(getClass().getClassLoader().getResourceAsStream(FORECAST_RESPONSE_PATH));
        ForecastResponse forecastWeatherResponse = gson.fromJson(forecastResponseJson, ForecastResponse.class);

        when(weatherApi.getCurrentWeather(anyString())).thenReturn(Observable.just(currentWeatherResponse));
        when(weatherApi.getForecast(anyString())).thenReturn(Observable.just(forecastWeatherResponse));
    }

    private void loadUnsuccessfulResponse() {
        String json = convertStreamToString(getClass().getClassLoader().getResourceAsStream(ERROR_RESPONSE_PATH));
        Response response = Response.error(404, ResponseBody.create(MediaType.parse("application/json") ,json));

        when(weatherApi.getCurrentWeather(anyString())).thenReturn(Observable.<CurrentWeatherResponse>error(new HttpException(response)));
        when(weatherApi.getForecast(anyString())).thenReturn(Observable.<ForecastResponse>error(new HttpException(response)));
    }

    @Test
    public void loadingWeather_correctlySetsProgress() throws Exception {
        loadSuccessfulResponses();
        weatherPresenter.loadWeather("Auckland");
        verify(weatherView).hideWeatherView();
        verify(weatherView).setLoading(true);
    }

    @Test
    public void loadingWeather_isSuccessful() throws Exception {
        loadSuccessfulResponses();
        weatherPresenter.loadWeather("Auckland");
        verify(weatherView).showWeatherView();
        verify(weatherView).updateList();
    }

    @Test
    public void loadingWeather_showsCorrectWindInfo() throws Exception {
        loadSuccessfulResponses();
        weatherPresenter.loadWeather("Auckland");

        ArgumentCaptor<WindInfo> windInfoArgumentCaptor = ArgumentCaptor.forClass(WindInfo.class);
        verify(weatherView).setWindDirection(windInfoArgumentCaptor.capture());
        assertEquals(windInfoArgumentCaptor.getValue().getDegree(), 236.502f, 0);
    }

    @Test
    public void loadingWeather_showsCorrectTemperatureInfo() throws Exception {
        loadSuccessfulResponses();
        weatherPresenter.loadWeather("Auckland");

        ArgumentCaptor<TemperatureInfo> temperatureInfoArgumentCaptor = ArgumentCaptor.forClass(TemperatureInfo.class);
        verify(weatherView).setTemperature(temperatureInfoArgumentCaptor.capture());
        assertEquals(temperatureInfoArgumentCaptor.getValue().getTemperature(), 15.38f, 0);
        assertEquals(temperatureInfoArgumentCaptor.getValue().getMinTemperature(), 15.38f, 0);
        assertEquals(temperatureInfoArgumentCaptor.getValue().getMaxTemperature(), 15.38f, 0);
    }

    @Test
    public void loadingWeather_showsCorrectDateAndTime() throws Exception {
        loadSuccessfulResponses();
        weatherPresenter.loadWeather("Auckland");

        verify(weatherView, atLeastOnce()).setDate("Sat, 14 Oct");
        verify(weatherView, atLeastOnce()).setTime("6:30 PM");
    }

    @Test
    public void loadingWeather_updatesBackgroundCorrectly() throws Exception {
        loadSuccessfulResponses();
        weatherPresenter.loadWeather("Auckland");

        verify(weatherView).updateBackground(18);
    }

    @Test
    public void loadingWeather_isUnsuccessful() throws Exception {
        loadUnsuccessfulResponse();
        weatherPresenter.loadWeather("Auckland");
        verify(weatherView).setError(R.string.generic_error_message);
        verify(weatherView, never()).updateList();
    }
}