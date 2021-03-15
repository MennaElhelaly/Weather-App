package com.forecast.weather.ui.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.forecast.weather.dataLayer.WeatherRepository
import com.forecast.weather.dataLayer.entity.Weather


class FavoriteCitesViewModel(val app: Application) : AndroidViewModel(app) {

    val weatherRepository : WeatherRepository = WeatherRepository(app)
    private val navigate: MutableLiveData<Weather> = MutableLiveData<Weather>()

    fun allCountries() :LiveData<List<Weather>> {

        return weatherRepository.returnAll()
    }
    fun deleteCountry(weather: Weather){
        weatherRepository.deleteItem(weather)
    }

    //navigation
    fun onClick(weather: Weather) {
        navigate.value = weather
    }

    fun getnavigation(): LiveData<Weather> {
        return navigate
    }

}