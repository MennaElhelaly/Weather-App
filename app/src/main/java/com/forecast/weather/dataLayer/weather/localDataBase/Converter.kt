package com.example.kotlinproject.dataLayer.entity

import androidx.room.TypeConverter
import com.forecast.weather.dataLayer.entity.*
import com.forecast.weather.dataLayer.entity.Alert
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun fromWetherToGesonx(list :List<WeatherCurrent>) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToWeatherx(gson: String):List<WeatherCurrent>{
        val type = object : TypeToken<List<WeatherCurrent>>() {}.type
        return Gson().fromJson(gson,type)
    }
    @TypeConverter
    fun fromWetherToGesonxx(list :List<WeatherDaily>) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToWeatherxx(gson: String):List<WeatherDaily>{
        val type = object : TypeToken<List<WeatherDaily>>() {}.type
        return Gson().fromJson(gson,type)
    }

    @TypeConverter
    fun fromWetherToGeson(list :List<WeatherHourly>) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToWeather(gson: String):List<WeatherHourly>{
        val type = object : TypeToken<List<WeatherHourly>>() {}.type
        return Gson().fromJson(gson,type)
    }

    @TypeConverter
    fun fromCurrentToGeson(list : Current) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToCurrrent(gson: String):Current{
        return Gson().fromJson(gson,Current::class.java)
    }

    @TypeConverter
    fun fromAlertToGeson(list :List<Alert>?) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToAlert(gson: String):List<Alert>?{
        val type = object : TypeToken<List<Alert>?>() {}.type
        return Gson().fromJson(gson,type)
    }


    @TypeConverter
    fun fromFeelsLikeToGeson(feelsLike: FeelsLike) : String{
        return Gson().toJson(feelsLike)
    }

    @TypeConverter
    fun fromGesonToFeelsLike(feelsLike: String) : FeelsLike{
        return Gson().fromJson(feelsLike,FeelsLike::class.java)
    }

    @TypeConverter
    fun fromRainToGeson(feelsLike: Rain) : String{
        return Gson().toJson(feelsLike)
    }

    @TypeConverter
    fun fromGesonToRain(feelsLike: String) : Rain{
        return Gson().fromJson(feelsLike,Rain::class.java)
    }



    @TypeConverter
    fun fromTemmpToGeson(temp: Temp) : String{
        return Gson().toJson(temp)
    }

    @TypeConverter
    fun fromGesonToTemp(temp: String) : Temp{
        return Gson().fromJson(temp,Temp::class.java)
    }


    @TypeConverter
    fun fromHourlyToGeson(list :List<Hourly>) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToHourly(gson: String):List<Hourly>{
        val type = object : TypeToken<List<Hourly>>() {}.type
        return Gson().fromJson(gson,type)
    }
    @TypeConverter
    fun fromDailyToGeson(list :List<Daily>) : String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJsonToDaily(gson: String):List<Daily>{
        val type = object : TypeToken<List<Daily>>() {}.type
        return Gson().fromJson(gson,type)
    }



}