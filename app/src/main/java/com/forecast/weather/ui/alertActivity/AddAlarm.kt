package com.forecast.weather.ui.alertActivity

import android.app.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.forecast.weather.R
import com.forecast.weather.dataLayer.entityAlarm.Alarm
import com.forecast.weather.databinding.ActivityAddAlarmBinding
import com.forecast.weather.ui.setAlarm
import java.text.SimpleDateFormat
import java.util.*

class AddAlarm : AppCompatActivity() {
    private lateinit var binding: ActivityAddAlarmBinding
    private lateinit var addAlarmViewModel: AddAlarmViewModel
    private lateinit var alarmType :String
    lateinit var alarmDate :String
    private lateinit var alarmTime :String
    private lateinit var timeInDay:String
    private lateinit var alarmEvent :String
    private lateinit var alarmDescription :String
    private  var periodTimeStart :Long =0
    private  var periodTimeEnd :Long=111
    var myHour:Int=0
    var myMinute:Int=0
    var myHourFrom:Int=0
    var myMinuteFrom:Int=0
    var myHourTo:Int=0
    var myMinuteTo:Int=0
    var myMonth:Int=0
    var myYear:Int=0
    var myDay:Int=0
    lateinit var  calendarDay:Calendar
    lateinit var  calendarFrom:Calendar
    lateinit var  calendarTo:Calendar
    lateinit var  calendarEnd:Calendar
    lateinit var date:Date


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        //hide actionBar
        val actionBar = supportActionBar
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityAddAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addAlarmViewModel = ViewModelProvider(this).get(AddAlarmViewModel::class.java)
        calendarFrom= Calendar.getInstance()
        calendarTo= Calendar.getInstance()
        calendarDay= Calendar.getInstance()
        calendarEnd= Calendar.getInstance()
        alarmType ="notification"
        timeInDay ="anytime"
        alarmEvent =getString(R.string.rain)

        updateUI()
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun updateUI() {
        soundClicked()
        clickEvent()
        chooseTypeOfTime()
        binding.alertTime.setOnClickListener {
            getTimeX()
        }
        binding.alertDate.setOnClickListener {
            getDate()
        }
        binding.fromTime.setOnClickListener {
            getTimeFrom()
        }
        binding.totime.setOnClickListener {
            getTimeToTime()
        }
        if (binding.editTextDes.text.toString().isEmpty()) {
            alarmDescription = getString(R.string.noDesc)
        }
        alarmDescription = binding.editTextDes.text.toString()
        
        saveClicked()
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun saveClicked(){
        binding.btnSave.setOnClickListener {
            alarmDescription = if (binding.editTextDes.text.toString().isEmpty()) {
                getString(R.string.noDesc)
            }else {
                binding.editTextDes.text.toString()
            }
            if (timeInDay == "anytime"){
                periodTimeStart=calendarDay.timeInMillis
            }
            else
            {
                periodTimeEnd=calendarTo.timeInMillis
            }

            if (binding.alertTime.text.isEmpty() || binding.alertDate.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.please), Toast.LENGTH_LONG).show()
            }
            else if(timeInDay.equals("period") && (binding.fromTime.text.isEmpty() || binding.totime.text.isEmpty()) ){
                    Toast.makeText(this, getString(R.string.please), Toast.LENGTH_LONG).show()
            }
            else {
                val alertSetTime="$myYear/$myMonth/$myDay/$myHour/$myMinute"
                val alarm= Alarm(
                        alarmType, alarmDate, alarmTime,
                        alarmEvent, timeInDay, periodTimeStart, periodTimeEnd,
                        alarmDescription, alertSetTime,calendarDay.timeInMillis,0,true
                )
                addAlarmViewModel.insertOneAlarm(alarm)
                addAlarmViewModel.returnId().observe(this) {
                    setAlarm(this,
                        it, alarmEvent, alarmDescription, myHour, myMinute, myDay,
                        myMonth, myYear, timeInDay, periodTimeStart, periodTimeEnd,alarmType,it
                    )
                    addAlarmViewModel.updateIdAlarm(it,it)
                    finish()
                }

            }
        }
    }
    private fun clickEvent()
    {   //type event
        binding.rain.setOnClickListener {
            binding.rain.isChecked = true
            binding.snow.isChecked = false
            binding.temp.isChecked = false
            binding.wind.isChecked = false
            binding.thunderStorm.isChecked = false
            binding.mist.isChecked = false
            alarmEvent = getString(R.string.rain)
        }
        binding.snow.setOnClickListener {
            binding.rain.isChecked = false
            binding.snow.isChecked = true
            binding.temp.isChecked = false
            binding.wind.isChecked = false
            binding.thunderStorm.isChecked = false
            binding.mist.isChecked = false
            alarmEvent = getString(R.string.snow)
        }
        binding.temp.setOnClickListener {
            binding.rain.isChecked = false
            binding.snow.isChecked = false
            binding.temp.isChecked = true
            binding.wind.isChecked = false
            binding.thunderStorm.isChecked = false
            binding.mist.isChecked = false
            alarmEvent = getString(R.string.cloud)
        }
        binding.wind.setOnClickListener {
            binding.rain.isChecked = false
            binding.snow.isChecked = false
            binding.temp.isChecked = false
            binding.wind.isChecked = true
            binding.thunderStorm.isChecked = false
            binding.mist.isChecked = false
            alarmEvent = getString(R.string.wind)
        }
        binding.thunderStorm.setOnClickListener {
            binding.rain.isChecked = false
            binding.snow.isChecked = false
            binding.temp.isChecked = false
            binding.wind.isChecked = false
            binding.thunderStorm.isChecked = true
            binding.mist.isChecked = false
            alarmEvent = getString(R.string.thunderStorm)
        }
        binding.mist.setOnClickListener {
            binding.rain.isChecked = false
            binding.snow.isChecked = false
            binding.temp.isChecked = false
            binding.wind.isChecked = false
            binding.thunderStorm.isChecked = false
            binding.mist.isChecked = true
            alarmEvent = getString(R.string.mistFog)
        }
    }
    private fun chooseTypeOfTime(){

        binding.anytime.setOnClickListener {
            binding.anytime.isChecked = true
            binding.periodOfTime.isChecked = false
            binding.time.visibility=View.GONE
            timeInDay="anytime"
        }
        binding.periodOfTime.setOnClickListener {
            binding.anytime.isChecked = false
            binding.periodOfTime.isChecked = true
            binding.time.visibility=View.VISIBLE
            timeInDay="period"
        }
    }
    private fun soundClicked(){
        //type of messsage
        binding.notification.setOnClickListener {
            binding.notification.isChecked = true
            binding.dialog.isChecked = false
            alarmType = "notification"
        }
        binding.dialog.setOnClickListener {
            binding.notification.isChecked = false
            binding.dialog.isChecked = true
            alarmType = "alarm"
        }
    }
    private fun getTimeX() {
        val hour = calendarDay[Calendar.HOUR_OF_DAY]
        val minute = calendarDay[Calendar.MINUTE]
        val mTimePicker= TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                myHour = selectedHour
                myMinute = selectedMinute
                val date = Date()
                date.hours = myHour
                date.minutes = myMinute
                binding.alertTime.text = SimpleDateFormat("HH:mm").format(date)
                alarmTime = SimpleDateFormat("HH:mm").format(date).toString()
                //add day
                calendarEnd.time = date
                calendarEnd.add(Calendar.DATE, 1)
                periodTimeEnd=calendarEnd.timeInMillis

            }, hour, minute, false
        )
        mTimePicker.setTitle(getString(R.string.selectAlarmTime))
        mTimePicker.show()
    }
    private fun getTimeFrom() {
        val hour = calendarFrom[Calendar.HOUR_OF_DAY]
        val minute = calendarFrom[Calendar.MINUTE]
        val mTimePicker= TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                myHourFrom = selectedHour
                myMinuteFrom = selectedMinute
                val date = Date()
                date.hours = myHourFrom
                date.minutes = myMinuteFrom
                binding.fromTime.text = SimpleDateFormat("HH:mm").format(date)
                calendarFrom.time = date
                periodTimeStart = calendarFrom.timeInMillis
            }, hour, minute, false
        )
        mTimePicker.setTitle(getString(R.string.alarmStart))
        mTimePicker.show()
    }
    private fun getTimeToTime() {
        val hour = calendarTo[Calendar.HOUR_OF_DAY]
        val minute = calendarTo[Calendar.MINUTE]
        val mTimePicker= TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                myHourTo = selectedHour
                myMinuteTo = selectedMinute
                val date = Date()
                date.hours = myHourTo
                date.minutes = myMinuteTo
                binding.totime.text = SimpleDateFormat("HH:mm").format(date)
                calendarTo.time = date
                periodTimeEnd = calendarTo.timeInMillis

            }, hour, minute, false
        )
        mTimePicker.setTitle(getString(R.string.alarmEnd))
        mTimePicker.show()
    }
    private fun getDate() {
        val year = calendarDay.get(Calendar.YEAR)
        val month = calendarDay.get(Calendar.MONTH)
        val day = calendarDay.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this, { _, yearOfDate, monthOfYear, dayOfMonth ->
                binding.alertDate.text = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year
                binding.alertDate.visibility = View.VISIBLE
                alarmDate = "$dayOfMonth/${monthOfYear + 1}/$yearOfDate"
                myMonth = monthOfYear + 1
                myYear = yearOfDate
                myDay = dayOfMonth
            }, year, month, day
        )
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()
    }
    
}