package com.forecast.weather.dataLayer.weather.localDataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.forecast.weather.dataLayer.entity.Weather

@Dao
interface WeatherDao {

    @Query("SELECT * FROM Weather")
    fun getAllWeather(): LiveData<List<Weather>>


    @Query("SELECT * FROM Weather WHERE timezone LIKE :weatherId")
    fun getOneWeather(weatherId: String): LiveData<Weather>

    @Query("SELECT * FROM Weather WHERE lat Like :latId and lon Like :lonId LIMIT 1 ")
    fun getOneWeatherByLatLon(latId: String, lonId: String): LiveData<Weather>

    @Query("SELECT * FROM Weather WHERE lat Like :latId and lon Like :lonId LIMIT 1 ")
    fun getOneWeatherToAlert(latId: String, lonId: String): Weather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weather: Weather)

    @Delete
    fun delete(weather: Weather)
}