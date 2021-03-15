package com.forecast.weather.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.forecast.weather.R
import com.forecast.weather.dataLayer.SharedPreference
import com.forecast.weather.dataLayer.entity.Alert
import com.forecast.weather.dataLayer.entity.Daily
import com.forecast.weather.dataLayer.entity.Hourly
import com.forecast.weather.databinding.FragmentHomeBinding
import com.forecast.weather.ui.notification.MyNotification
import com.forecast.weather.ui.notification.WeatherReceiver

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    lateinit var shared : SharedPreference
    var dailyAdapter = DailyAdapter(arrayListOf())
    var hourlyAdapter = HourlyAdapter(arrayListOf())
    lateinit var myLat:String
    lateinit var myLong:String
    lateinit var notificationHelper :MyNotification

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeViewModel=ViewModelProvider(this).get(HomeViewModel::class.java)

        val shareded= PreferenceManager.getDefaultSharedPreferences(this.context)

        val first =shareded.getBoolean("firstTime",false)
        if (!first){
            findNavController().navigate(R.id.setting)
        }
        shared= SharedPreference(requireContext())

        if(shared.fromMap && shared.myLocation==false){
            myLat =shared.mapLat.toString()
            myLong =shared.mapLong.toString()
        }
        else {
            myLat =shared.lat.toString()
            myLong =shared.long.toString()
        }
        initUI()
        getCurrent(myLat,myLong)
        return binding.root
    }

    private fun initUI() {
        binding.recyclerDays.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dailyAdapter
        }
        binding.recyclerHours.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = hourlyAdapter
        }
    }

    private fun getCurrent(lat: String, lan: String){
        homeViewModel.fetchWeatherByID(lat, lan).observe(viewLifecycleOwner, {
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
                if (it.current.weather[0].icon.contains("n",ignoreCase = true))
                {
                    binding.home.setBackgroundResource(R.drawable.night)
                    binding.scrollBack.setBackgroundResource(R.drawable.night)
                }

                updateDailyList(it.daily)
                updateHourlyList(it.hourly)
                loadAlert(it.alerts)
            }
        })
    }
    private fun updateDailyList(it: List<Daily>) {
        dailyAdapter.updateDays(it)
        binding.recyclerDays.visibility=View.VISIBLE
    }
    private fun updateHourlyList(it: List<Hourly>) {
        hourlyAdapter.updateHours(it)
        binding.recyclerHours.visibility=View.VISIBLE
    }
    private fun loadAlert(alerts: List<Alert>?) {
        if (alerts!=null){
            val intent = Intent(this.context, WeatherReceiver::class.java)
            intent.putExtra("event"," ${alerts[0].event}")
            intent.putExtra("desc", " ")
            notificationHelper = MyNotification(requireActivity(),intent)
            val notificationBuilder = notificationHelper.channelNotification
            notificationHelper.manager!!.notify(10999, notificationBuilder.build())
        }
    }
}