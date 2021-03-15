package com.forecast.weather.ui.favorite_datails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.forecast.weather.dataLayer.WeatherRepository
import com.forecast.weather.dataLayer.entity.Weather

class FavouriteDViewModel(app : Application) : AndroidViewModel(app) {
    val weatherRepository : WeatherRepository = WeatherRepository(app)

    fun fetchWeatherByID(lat: String, lng: String): LiveData<Weather> {
        return weatherRepository.getAndInsertWeather(lat, lng)
    }
    fun fetchWeather(lat: String, lng: String): LiveData<List<Weather>> {
        return weatherRepository.getWeathers(lat, lng)
    }

}