package com.forecast.weather.ui.alertFragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.forecast.weather.R
import com.forecast.weather.dataLayer.entityAlarm.Alarm
import com.forecast.weather.databinding.FragmentAlertBinding
import com.forecast.weather.ui.alertActivity.AddAlarm

class AlertFragment : Fragment() {

    private lateinit var alertViewModel: AlertViewModel
    private lateinit var binding: FragmentAlertBinding

    lateinit var alarmAdapter: AlertAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        alertViewModel = ViewModelProvider(this).get(AlertViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alert, container, false)
        alarmAdapter = AlertAdapter(arrayListOf(),alertViewModel)
        initUI()
        buttonAlarmClick()
        getCurrent()
        alertViewModel.notificationID.observe(viewLifecycleOwner,{
            this.context?.let { it1 -> alertViewModel.cancelAlertContext(it1,it) }
        })
        return binding.root
    }
    private fun initUI() {
        binding.recyclerAlarm.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = alarmAdapter
        }
        val itemTouchHelper = ItemTouchHelper(SwipeTodeleteAlarm(alarmAdapter,this.requireContext()))
        itemTouchHelper.attachToRecyclerView(binding.recyclerAlarm)
    }
    private fun buttonAlarmClick()
    {
        binding.bottunAddAlarm.setOnClickListener {
            activity?.let {
                val intent = Intent(it, AddAlarm::class.java)
                it.startActivity(intent)
            }
        }
    }
    fun getCurrent(){
        alertViewModel.allAlarms().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding.back.setBackgroundColor(Color.WHITE)
                binding.empty.visibility=View.GONE
                updateAlarmList(it)
            }
        })
    }
    private fun updateAlarmList(it: List<Alarm>) {
        alarmAdapter.updateAlerts(it)
    }

}