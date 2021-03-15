package com.forecast.weather.dataLayer.remoteDB

import com.forecast.weather.dataLayer.entity.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val appId = "416c3f7d60f73a4f8f76c658c93cf3b7"
interface NetworkApi {
    @GET("data/2.5/onecall?")
    fun getCurrentData(@Query("lat") lat: String,
                 @Query("lon") lon: String,
                 @Query("lang") lang: String,
                 @Query("appId") appId: String,
                 @Query("exclude") exclude : String,
                 @Query("units") units : String): Call<Weather>

}