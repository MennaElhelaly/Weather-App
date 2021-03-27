package com.forecast.weather.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.forecast.weather.dataLayer.WeatherRepository
import com.forecast.weather.dataLayer.entity.Weather


class HomeViewModel(app :Application) : AndroidViewModel(app) {


    val weatherRepository : WeatherRepository = WeatherRepository(app)

    fun fetchWeatherByID(lat: String, lng: String): LiveData<Weather> {
        return weatherRepository.getAndInsertWeather(lat, lng)
    }

}