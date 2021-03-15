package com.forecast.weather.ui.notification

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.forecast.weather.R
import com.forecast.weather.dataLayer.SharedPreference
import com.forecast.weather.dataLayer.WeatherRepository
import com.forecast.weather.dataLayer.entity.Hourly
import com.forecast.weather.ui.alertFragment.AlertViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class WeatherReceiver : BroadcastReceiver() {
    lateinit var  reposatory:WeatherRepository
    lateinit var  alertViewModel: AlertViewModel
    lateinit var shared : SharedPreference
    lateinit var myLat :String
    lateinit var myLong :String
    var from :Long =1
    var to :Long=1



    override fun onReceive(context: Context, intent: Intent) {
        shared= SharedPreference(context)
        if(shared.fromMap && shared.myLocation==false){
            myLat =shared.mapLat.toString()
            myLong =shared.mapLong.toString()
        }
        else {
            myLat =shared.lat.toString()
            myLong =shared.long.toString()
        }
        reposatory= WeatherRepository(context.applicationContext as Application)
        alertViewModel= AlertViewModel(context.applicationContext as Application)
        val event = intent.getStringExtra("event")
        val id = intent.getIntExtra("id",0)
        val timePeriod = intent.getStringExtra("timePeriod")
        val fromTime =intent.getLongExtra("fromTime",0)
        val toTime =intent.getLongExtra("toTime",0)
        val typeOfAlarm=intent.getStringExtra("type")
        val roomId =intent.getIntExtra("roomId",0)
        val myDate =Calendar.getInstance()
        val weatherData = reposatory.getOneWeatherToAlert(myLat,myLong)

        if (timePeriod=="period"){
            from =fromTime.toString().substring(0,10).toLong()
            to = toTime.toString().substring(0,10).toLong()
        }

        Log.i("hh", "$toTime  < ${myDate.timeInMillis}")

        if (toTime < myDate.timeInMillis){
            //cancel and delete
            CoroutineScope(Dispatchers.IO).launch {
                reposatory.deleteAlertById(roomId)
            }
            alertViewModel.cancelAlertContext(context,id)
            Log.i("hh", "cancel")

        }
        else if(timePeriod =="anytime" && typeOfAlarm =="alarm"){
            if(weatherData.hourly.any { it.weather[0].description.contains(event+"",ignoreCase = true) }){
                val notificationHelper = NotificationWithSound(context,intent)
                val notification: NotificationCompat.Builder = notificationHelper.channelNotification
                notificationHelper.manager?.notify(1, notification.build())
            }

        }else if(timePeriod=="period"&& toTime >= myDate.timeInMillis && typeOfAlarm =="alarm"){
            if(weatherData.hourly.filter { it.dt in (from + 1) until to }.any{ it.weather[0].description.contains(event+"",ignoreCase = true)}){
                val notificationHelper = NotificationWithSound(context,intent)
                val notification: NotificationCompat.Builder = notificationHelper.channelNotification
                notificationHelper.manager?.notify(1, notification.build())
            }
        }else{
             if(timePeriod =="anytime"){
                 if (weatherData.hourly.any { it.weather[0].description.contains(event+"",ignoreCase = true) }){
                    val notificationHelper = MyNotification(context,intent)
                    val notification: NotificationCompat.Builder = notificationHelper.channelNotification
                    notificationHelper.manager?.notify(1, notification.build())
                }
            }else if(timePeriod== "period"&& toTime >= myDate.timeInMillis ){
                 if(weatherData.hourly.filter { it.dt in (from + 1) until to }.any{ it.weather[0].description.contains(event+"",ignoreCase = true)}){
                    val notificationHelper = MyNotification(context,intent)
                    val notification: NotificationCompat.Builder = notificationHelper.channelNotification
                    notificationHelper.manager?.notify(1, notification.build())
                }
            }
        }

    }
}


