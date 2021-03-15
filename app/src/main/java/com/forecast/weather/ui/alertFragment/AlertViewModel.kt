package com.forecast.weather.ui.alertFragment

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.forecast.weather.dataLayer.WeatherRepository
import com.forecast.weather.dataLayer.entityAlarm.Alarm
import com.forecast.weather.ui.notification.WeatherReceiver


class AlertViewModel(val app: Application) : AndroidViewModel(app) {

    val weatherRepository: WeatherRepository = WeatherRepository(app)
    private val navigate: MutableLiveData<Alarm> = MutableLiveData<Alarm>()
    val notificationID :MutableLiveData<Int> = MutableLiveData<Int>()

    fun allAlarms(): LiveData<List<Alarm>> {
        return weatherRepository.getAllAlerts()
    }
    fun updateAlarm(id: Int, switch:Boolean){
        weatherRepository.updateAlarm(id,switch)
    }
    fun updateIdAlarm(id: Int, newId: Int){
        weatherRepository.updateIdAlarm(id,newId)
    }

    fun deleteAlarm(alarm: Alarm) {
        weatherRepository.deleteAlarm(alarm)
    }
    //navigation
    fun onClick(alarm: Alarm) {
        navigate.value = alarm
    }

    fun cancelAlertContext(context: Context, id: Int){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val myIntent = Intent(context, WeatherReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, id, myIntent,  PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager!!.cancel(pendingIntent)
        Log.i("hh","cancel Done")
    }

}

