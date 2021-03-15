package com.forecast.weather.ui.favorite_datails

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.forecast.weather.R
import com.forecast.weather.dataLayer.SharedPreference
import com.forecast.weather.dataLayer.entity.Daily
import com.forecast.weather.dataLayer.entity.Hourly
import com.forecast.weather.databinding.ActivityFavouriteDetailsBinding
import com.forecast.weather.ui.home.*

class FavouriteDetails : AppCompatActivity() {
    private lateinit var favouriteDViewModel: FavouriteDViewModel
    private lateinit var binding: ActivityFavouriteDetailsBinding
    lateinit var shared : SharedPreference
    var dailyAdapter = DailyAdapter(arrayListOf())
    var hourlyAdapter = HourlyAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar?.hide()
        binding = ActivityFavouriteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        favouriteDViewModel = ViewModelProvider(this).get(FavouriteDViewModel::class.java)
        shared= SharedPreference(this)

        initUI()
        val lat=intent.getStringExtra("Lat")
        val long=intent.getStringExtra("Long")
        getCurrent(lat.toString(), long.toString())

    }
    private fun initUI() {
        binding.recyclerDays.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = dailyAdapter
        }
        binding.recyclerHours.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = hourlyAdapter
        }
    }
    fun getCurrent(lat: String, lan: String){
        favouriteDViewModel.fetchWeatherByID(lat, lan).observe(this, {
            if (it !=null) {
                binding.city.text = it.timezone//last one test
                if (shared.units.equals("metric")) {
                    binding.temp.text = it.current.temp.toInt().toString() + getString(R.string.c)
                    binding.txtWindSpeed.text = it.current.wind_speed.toString() +" "+getString(R.string.ms)
                }
                else if (shared.units.equals("imperial")){
                    binding.temp.text = it.current.temp.toInt().toString() + getString(R.string.f)
                    binding.txtWindSpeed.text = it.current.wind_speed.toString() +" "+getString(R.string.mh)
                }
                else {
                    binding.temp.text = it.current.temp.toInt().toString() + getString(R.string.k)
                    binding.txtWindSpeed.text = it.current.wind_speed.toString() +" "+getString(R.string.ms)
                }
                binding.description.text = it.current.weather[0].description
                binding.date.text = getDateString(it.current.dt.toLong(),shared.language.toString())

                binding.txtClouds.text = it.current.clouds.toString()+ "%"
                binding.txtHumadity.text = it.current.humidity.toString() + "%"
                binding.txtPressure.text = it.current.pressure.toString()+" "+getString(R.string.pa)
                when (it.current.weather[0].icon) {
                    "01n" -> {
                        binding.icon.setImageResource(R.drawable.ic_moon)
                    }
                    "01d" -> {
                        binding.icon.setImageResource(R.drawable.ic_sun)
                    }
                    else -> {
                        loadImage(binding.icon,it.current.weather[0].icon)

                    }
                }
                /*val hour= getHours(it.current.dt.toLong()).split(" ").get(3)
                if (hour.toInt() <7 || hour.toInt()>17)
                {
                    binding.details.setBackgroundResource(R.drawable.night)
                    binding.scroll.setBackgroundResource(R.drawable.night)
                }*/
                if (it.current.weather[0].icon.contains("n",ignoreCase = true))
                {
                    binding.details.setBackgroundResource(R.drawable.night)
                    binding.scroll.setBackgroundResource(R.drawable.night)
                }
                updateDailyList(it.daily)
                updateHourlyList(it.hourly)
            }
        })
    }
    private fun updateDailyList(it: List<Daily>) {
        dailyAdapter.updateDays(it)
    }
    private fun updateHourlyList(it: List<Hourly>) {
        hourlyAdapter.updateHours(it)
    }
    override fun onPause() {
        finish()
        super.onPause()
    }

}