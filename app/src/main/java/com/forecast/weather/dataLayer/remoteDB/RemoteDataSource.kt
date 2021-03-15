package com.forecast.weather.dataLayer.remoteDB

import com.forecast.weather.dataLayer.entity.Weather
import retrofit2.Call

class RemoteDataSource(private var networkInterface : NetworkApi = NetworkService.createRetrofit() ) {

    fun getCurrentLocationWeather(
            latitude: String,
            longitude: String,
            languageCode: String,
            units: String
    ): Call<Weather> {
        return networkInterface
                .getCurrentData(
                        lat = latitude,
                        lon = longitude,
                        exclude = "minutely",
                        lang = languageCode,
                        units = units,
                        appId = "416c3f7d60f73a4f8f76c658c93cf3b7"
                )
    }


}