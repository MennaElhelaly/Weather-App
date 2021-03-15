package com.forecast.weather.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.forecast.weather.dataLayer.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel(application: Application) : AndroidViewModel(application) {

    val weatherRepository : WeatherRepository = WeatherRepository(application)

    fun  insertOneWeather(lat: String, lng: String) {
        CoroutineScope(Dispatchers.IO).launch {
            weatherRepository.insertInDB(lat, lng)
        }
    }

}
