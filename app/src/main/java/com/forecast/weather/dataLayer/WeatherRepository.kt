package com.forecast.weather.dataLayer

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.forecast.weather.dataLayer.entity.Weather
import com.forecast.weather.dataLayer.entityAlarm.Alarm
import com.forecast.weather.dataLayer.weather.localDataBase.LocalDB
import com.forecast.weather.dataLayer.remoteDB.RemoteDataSource
import com.forecast.weather.dataLayer.weather.localDataBase.LocalAlarmDB
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response


class WeatherRepository(application: Application) {
    private val remoteDataSource:RemoteDataSource = RemoteDataSource()
    private val localDataSource:LocalDB = LocalDB(application)
    private val localDataSourceAlarm =LocalAlarmDB(application)
    val id :MutableLiveData<Int> =MutableLiveData<Int>()
    private val shared :SharedPreference= SharedPreference(application.applicationContext)

    fun insertInDB(latitude: String, longitude: String){
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getCurrentLocationWeather(
                    latitude,
                    longitude,
                    shared.language!!,
                    shared.units!!
            ).enqueue(object : retrofit2.Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    response.body().let {
                        if (it != null) {
                            localDataSource.insertWeather(it)
                        }
                    }
                }
                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    t.printStackTrace()
                    Log.i("hh", "fail")
                }
            })
        }
    }
    fun getWeathers(latitude: String, longitude: String): LiveData<List<Weather>> {
        insertInDB(latitude,longitude)
        return localDataSource.getAllWeather()
    }
    fun getAndInsertWeather(latitude: String, longitude: String): LiveData<Weather> {
        insertInDB(latitude,longitude)
        return localDataSource.getWeatherByLatLon(latitude,longitude)
    }
    fun returnAll():LiveData<List<Weather>>{
        return localDataSource.getAllWeather()
    }

    fun getOneWeatherToAlert(lat:String,lon:String): Weather {
        return localDataSource.getOneWeatherToAlert(lat,lon)
    }
    fun deleteItem(weather: Weather){
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.delete(weather)
        }
    }
    fun insertAlert(alarm: Alarm){
        CoroutineScope(Dispatchers.IO).launch {
            val notId =localDataSourceAlarm.insertAlarm(alarm)
            id.postValue(notId.toInt())
        }
    }
    fun returnId():LiveData<Int>{
        return id
    }
    fun getAllAlerts():LiveData<List<Alarm>>{
        return localDataSourceAlarm.getAllAlarms()
    }
    fun deleteAlarm(alarm: Alarm){
        CoroutineScope(Dispatchers.IO).launch {
            localDataSourceAlarm.delete(alarm)
        }
    }
    fun deleteAlertById(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            localDataSourceAlarm.deleteById(id)
        }
    }
    fun updateAlarm(id: Int, switch:Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            localDataSourceAlarm.updateItem(id,switch)
        }
    }
    fun updateIdAlarm(id: Int, newId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            localDataSourceAlarm.updateIdAlarm(id,newId)
        }
    }

}
