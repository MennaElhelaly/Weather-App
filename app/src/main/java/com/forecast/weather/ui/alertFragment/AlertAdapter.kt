package com.forecast.weather.ui.alertFragment

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.forecast.weather.R
import com.forecast.weather.dataLayer.SharedPreference
import com.forecast.weather.dataLayer.entityAlarm.Alarm
import com.forecast.weather.ui.setAlarm
import kotlinx.android.synthetic.main.row_alarm.view.*

class AlertAdapter(var alerts: ArrayList<Alarm>, var alertViewModel: AlertViewModel) :
        RecyclerView.Adapter<AlertAdapter.AlarmViewHolder>() {

    fun updateAlerts(newAlert: List<Alarm>) {
        alerts.clear()
        alerts.addAll(newAlert)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = AlarmViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_alarm, parent, false)
    )

    override fun getItemCount() = alerts.size

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(alerts[position])
        holder.itemView.setOnClickListener {
            alertViewModel.onClick(alerts[position])
        }
        holder.itemView.switchOn.setOnClickListener{
            val alert =alerts[position]
            val alertTime =alert.alertTime.split("/")
            if(alert.alarmOn){
                alertViewModel.updateAlarm(alert.alarmId,false)
                alertViewModel.cancelAlertContext(it.context,alert.alarmNewID)
            }
            else
            {
                alertViewModel.updateAlarm(alert.alarmId,true)
                setAlarm(it.context,alert.alarmNewID+1000,alert.event,alert.description,
                        alertTime[3].toInt(),alertTime[4].toInt(),alertTime[2].toInt(),alertTime[1].toInt(),
                        alertTime[0].toInt(),alert.timeInDay,alert.periodTimeStart,alert.periodTimeEnd,alert.alarmType,alert.alarmId)
                alertViewModel.updateIdAlarm(alert.alarmId,alert.alarmNewID+1000)
            }
        }

    }

    fun deleteItem(pos: Int) {
        alertViewModel.notificationID.value=alerts[pos].alarmNewID
        alertViewModel.deleteAlarm(alerts[pos])
        alerts.removeAt(pos)
        notifyItemRemoved(pos)
    }

    class AlarmViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val type = view.typeofAlarm
        private val time = view.time_alarm
        private val searchTime = view.typeDay
        private val description = view.descriptionOfAlarm
        private val btnOn = view.switchOn
        fun bind(alarm: Alarm) {

            val shared= SharedPreference(view.context)
            if(shared.language=="en"){
                type.text = alarm.event+" "+ view.context.getString(R.string.alarmNoAl)
            }else {
                type.text = view.context.getString(R.string.alarmNoAl)+" "+alarm.event
            }
            time.text=" "+alarm.date+" "+ view.context.getString(R.string.at)+" "+alarm.time
            description.text =" "+ alarm.description
            this.btnOn.isChecked= alarm.alarmOn
            if (alarm.timeInDay=="anytime") {
                searchTime.text=" "+view.context.getString(R.string.anyTime)
            }else {
                searchTime.text=" "+view.context.getString(R.string.periodoftime)
            }
        }
    }

}