package com.forecast.weather.ui.notification

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.forecast.weather.R


class NotificationWithSound(context: Context, intent: Intent) : ContextWrapper(context) {
    private var mManager: NotificationManager? = null
    private lateinit var intent: Intent

    companion object {
        const val channelID = "channelID"
        const val channelName = "ChannelName"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.intent = intent
            createChannel()
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()

            val soundUri = Uri.parse(
                    "android.resource://" +
                            applicationContext.packageName +
                            "/" +
                            R.raw.let
            )

            val notificationChannel = NotificationChannel(
                    channelID,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.setSound(soundUri, audioAttributes)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            manager!!.createNotificationChannel(notificationChannel)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
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
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setSound(Uri.parse(
                        "android.resource://" +
                                applicationContext.packageName +
                                "/" +
                                R.raw.let
                ), AudioManager.STREAM_NOTIFICATION)

}
