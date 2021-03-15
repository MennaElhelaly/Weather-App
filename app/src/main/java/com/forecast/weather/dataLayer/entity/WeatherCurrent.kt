package com.forecast.weather.dataLayer.entity

data class WeatherCurrent(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)