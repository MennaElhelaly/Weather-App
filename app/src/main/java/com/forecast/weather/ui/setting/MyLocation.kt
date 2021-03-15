package com.forecast.weather.ui.setting

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.forecast.weather.R
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener

class MyLocation(var context: Context) {
    var PERMISSION_ID = 55
    var location: MutableLiveData<Location> = MutableLiveData<Location>()
    private var fusedLocationClient: FusedLocationProviderClient =LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                //Find Last Location
                fusedLocationClient.getLastLocation()
                        .addOnCompleteListener(OnCompleteListener<Location> { task -> //ask about location in every click button
                           // location.value = task.result //task(like thread) has my location
                            requestNewLocationData()
                            if (task.result == null) {
                                //if he did not find location saved .. will ask about fresh location
                                requestNewLocationData();
                                location.value = task.result //task(like thread) has my location

                            }
                            else{
                                location.value = task.result //task(like thread) has my location
                            }
                        })
            } else {
                Toast.makeText(context,context.getString(R.string.turnOn), Toast.LENGTH_SHORT).show()
                //open sitting to user to enable it
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1 //appear one time // by default infinity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                object : LocationCallback() {
                    //it called every time and every space to appear the location
                    override fun onLocationResult(locationResult: LocationResult) {
                        LocationServices.getFusedLocationProviderClient(context)
                                .removeLocationUpdates(this)
                        if (locationResult.locations.size > 0) {
                            val latestLocationIndex = locationResult.locations.size - 1

                            val latitude = locationResult.locations[latestLocationIndex].latitude
                            val longitude = locationResult.locations[latestLocationIndex].latitude
                            location.value?.longitude = longitude
                            location.value?.latitude = latitude
                        }
                    }
                },
                Looper.myLooper()
        ) //thread..main thread
    }

    private fun checkPermission(): Boolean {
        //check if permission i want in app is accepted or not ..... in run time
        return ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        // first pop up in application ...requestPermissions to  things i want to access....
        //id for permission to can but another permissions with another id
        ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
        )
    }

    //GPS Enabled or not
    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    fun showSettingsAlert() {
        val alertDialog = AlertDialog.Builder(
                this.context)
        alertDialog.setTitle("SETTINGS")
        alertDialog.setMessage("Enable Location Provider, IN settings menu?")
        alertDialog.setPositiveButton("Settings"
        ) { dialog, which ->
            val intent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            this.context.startActivity(intent)
        }
        alertDialog.setNegativeButton("Cancel"
        ) { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }


}