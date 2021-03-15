package com.forecast.weather.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forecast.weather.R
import com.forecast.weather.dataLayer.SharedPreference
import com.forecast.weather.dataLayer.entity.Hourly
import kotlinx.android.synthetic.main.col_hour.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HourlyAdapter(var hours: ArrayList<Hourly>) :
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {
    fun updateHours(newHour: List<Hourly>) {
        hours.clear()
        hours.addAll(newHour)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = HourlyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.col_hour, parent, false)
    )
    override fun getItemCount() = hours.size
    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(hours[position],position)
    }
    class HourlyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val time = view.txtHour
        private val temp = view.txtTempp
        private val imageView = view.iconW
        fun bind(hour: Hourly,position: Int) {
            val shared= SharedPreference(view.context)

            when (hour.weather[0].icon) {

                "01n" -> {
                    imageView.setImageResource(R.drawable.ic_moon)
                }
                "01d" -> {
                    imageView.setImageResource(R.drawable.ic_sun)
                }
                else -> {
                    loadImage(imageView,hour.weather[0].icon)

                }
            }
            if(shared.language=="en"){
                if (position==0){
                    time.text = view.context.getString(R.string.now)
                }
                else{
                    time.text = getDateStrings(hour.dt.toLong(),"en").split(",").get(1)+
                            getDateStrings(hour.dt.toLong(),"en").split(",").get(3)
                }
            }else {
                if (position==0){
                    time.text =" "+ view.context.getString(R.string.now)+" "
                }
                else{
                    time.text = getDateStrings(hour.dt.toLong(),"ar").split(",").get(1)+
                            getDateStrings(hour.dt.toLong(),"ar").split(",").get(3)
                }
            }

            temp.text=hour.temp.toInt().toString()+"°"
        }
        private fun getDateStrings(time: Long,language: String) : String {
            val locale = Locale(language)
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy ,hh,:mm , a", locale)
            return simpleDateFormat.format(time * 1000L)
        }

    }

}