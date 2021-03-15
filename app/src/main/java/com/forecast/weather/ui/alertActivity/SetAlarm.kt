package com.forecast.weather.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.forecast.weather.R
import com.forecast.weather.ui.notification.WeatherReceiver
import java.util.*

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun setAlarm(context: Context,
             id: Int,
             event: String,
             desc: String,
             hour: Int,
             min: Int,
             day: Int,
             month: Int,
             year: Int,
             timeOfAlarm: String,
             from: Long,
             to: Long,
             type:String,
alarm:Long) {
    val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
    Log.i("hh"," alarrrrm $id $event $hour $min $day $month $year $timeOfAlarm $from $to  $alarm  $type  ")
    val intentA = Intent(context, WeatherReceiver::class.java)
    intentA.putExtra("event", event)
    intentA.putExtra("alarm", alarm)
    intentA.putExtra("id", id)
    intentA.putExtra("desc", desc)
    intentA.putExtra("fromTime", from)
    intentA.putExtra("toTime", to)
    intentA.putExtra("timePeriod", timeOfAlarm)
    intentA.putExtra("type", type)
    val pendingIntentA = PendingIntent.getBroadcast(context, id, intentA, 0)
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, min)
    calendar[Calendar.MONTH] = month - 1
    calendar[Calendar.DATE] = day
    calendar[Calendar.YEAR] = year
    calendar[Calendar.SECOND] = 0
    val alarmtime: Long = calendar.timeInMillis
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmtime, pendingIntentA)
    Toast.makeText(context,context.getString(R.string.alarmAdded), Toast.LENGTH_LONG).show()
    context.registerReceiver(WeatherReceiver(), IntentFilter())
}
