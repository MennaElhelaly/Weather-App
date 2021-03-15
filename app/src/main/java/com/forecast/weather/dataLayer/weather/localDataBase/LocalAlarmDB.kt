package com.forecast.weather.dataLayer.weather.localDataBase

import android.app.Application
import androidx.lifecycle.LiveData
import com.forecast.weather.dataLayer.entityAlarm.Alarm



class LocalAlarmDB(application: Application) {

    private val alarmDao = WeatherDB.getInstance(application).alarmDao()

    fun getAllAlarms(): LiveData<List<Alarm>> {
        return alarmDao.getAllAlarms()
    }

    fun insertAlarm(alarm: Alarm):Long{
        return alarmDao.insertAlarm(alarm)
    }
    fun updateItem(id: Int, switch: Boolean){
        alarmDao.updateItem(id, switch)
    }
    fun updateIdAlarm(id: Int, newId: Int){
        alarmDao.updateIdAlarm(id, newId)
    }
    fun deleteById(id:Int){
        alarmDao.deleteById(id)
    }

    fun delete(alarm: Alarm) {
        alarmDao.delete(alarm)
    }


}