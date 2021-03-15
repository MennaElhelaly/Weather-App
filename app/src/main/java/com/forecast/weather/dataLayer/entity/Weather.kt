package com.forecast.weather.dataLayer.entity

import androidx.room.Entity

@Entity(primaryKeys = arrayOf("timezone"))
data class Weather(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val alerts:List<Alert>?,
)