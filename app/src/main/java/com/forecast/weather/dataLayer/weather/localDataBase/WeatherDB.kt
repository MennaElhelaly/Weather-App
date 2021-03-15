package com.forecast.weather.dataLayer.weather.localDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlinproject.dataLayer.entity.Converter
import com.forecast.weather.dataLayer.entityAlarm.Alarm
import com.forecast.weather.dataLayer.entity.Weather


@Database(
    entities = arrayOf(Weather::class,Alarm::class),
    version = 2,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class WeatherDB : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDB? = null
        fun getInstance(context: Context): WeatherDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherDB::class.java,
                        "weather_database"
                    )
                        .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
