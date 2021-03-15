package com.forecast.weather.ui.home

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

fun getDateStrings(time: Long,language: String) : String {
    val locale = Locale(language)
    val simpleDateFormat = SimpleDateFormat("dd MMMM,yyyy,  hh:mm a", locale)
    return simpleDateFormat.format(time * 1000L)
}
fun loadImage(imageView: ImageView, string: String) {
    Glide.with(imageView)  //2
        .load("https://openweathermap.org/img/wn/$string@2x.png")
        .centerCrop() //4
        .into(imageView)
}
fun getDateString(time: Long,language: String) : String {
    val locale = Locale(language)
    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy  hh:mm a", locale)
    return simpleDateFormat.format(time * 1000L)
}

