package com.forecast.weather.dataLayer.entityAlarm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(

        val alarmType:String,
        val date:String,
        val time:String,
        val event:String,
        val timeInDay:String,
        val periodTimeStart :Long,
        val periodTimeEnd :Long,
        val description: String,
        val alertTime:String,
        val alarmLong:Long,
        val alarmOn:Boolean
)
{
        @PrimaryKey(autoGenerate = true)
        var alarmId: Int = 0
}