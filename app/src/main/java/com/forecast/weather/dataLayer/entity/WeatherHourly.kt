package com.forecast.weather.dataLayer.entity

data class WeatherHourly(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)