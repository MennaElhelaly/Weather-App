package com.forecast.weather.dataLayer.remoteDB

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val BASE_URL = "https://api.openweathermap.org/"
    fun createRetrofit(): NetworkApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(NetworkApi::class.java)
    }
}