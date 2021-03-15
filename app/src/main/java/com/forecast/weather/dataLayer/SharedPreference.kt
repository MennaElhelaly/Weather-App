package com.forecast.weather.dataLayer

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

class SharedPreference(var context: Context) {

    val sp = PreferenceManager.getDefaultSharedPreferences(context)

    val myLocation = sp.getBoolean("USE_DEVICE_LOCATION", false)
    val fromMap = sp.getBoolean("CUSTOM", false)

    val units= sp.getString("UNIT_SYSTEM", "metric")
    val language = sp.getString("LANGUAGE_SYSTEM", "en")

    var lat = sp.getString("lat", "30.033333")
    var long = sp.getString("long", "31.233334")

    var mapLat = sp.getString("mapLat", "30.033333")
    var mapLong = sp.getString("mapLong", "31.233334")



}