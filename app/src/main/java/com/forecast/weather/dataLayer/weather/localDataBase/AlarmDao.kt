package com.forecast.weather.dataLayer.weather.localDataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.forecast.weather.dataLayer.entityAlarm.Alarm


@Dao
interface AlarmDao {

    @Query("SELECT * FROM Alarm")
    fun getAllAlarms(): LiveData<List<Alarm>>

    @Query("DELETE FROM Alarm WHERE alarmId LIKE :alarmId")
    fun deleteById(alarmId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(alarm: Alarm):Long

    @Query("UPDATE Alarm SET alarmOn = :switch WHERE alarmId LIKE :id ")
    fun updateItem(id: Int, switch: Boolean)

    @Delete()
    fun delete(alarm: Alarm)
}