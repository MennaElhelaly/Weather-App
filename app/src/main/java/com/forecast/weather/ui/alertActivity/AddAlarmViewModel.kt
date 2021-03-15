package com.forecast.weather.ui.alertActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.forecast.weather.dataLayer.WeatherRepository
import com.forecast.weather.dataLayer.entityAlarm.Alarm

class AddAlarmViewModel(app : Application) : AndroidViewModel(app) {

    val weatherRepository : WeatherRepository = WeatherRepository(app)

    fun  insertOneAlarm(alarm: Alarm) {
        weatherRepository.insertAlert(alarm)
    }
    fun returnId():LiveData<Int>{
        return weatherRepository.returnId()
    }


}