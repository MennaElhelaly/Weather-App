package com.forecast.weather.ui.notification

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.forecast.weather.R


class MyNotification(context: Context, intent: Intent) : ContextWrapper(context) {
    private var mManager: NotificationManager? = null
    private lateinit var intent: Intent

    companion object {
        const val channelID = "channelID2"
        const val channelName = "ChannelName2"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.intent = intent
            createChannel()
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.lightColor = Color.BLUE
        channel.enableVibration(true)
        manager!!.createNotificationChannel(channel)
    }
    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }

    val channelNotification: NotificationCompat.Builder
        get() = NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle(getString(R.string.weatherAlarm))
            .setContentText(getString(R.string.takeCareAlert) +" "+intent.getStringExtra("event") + "  " + intent.getStringExtra("desc"))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setOngoing(false)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))


}
