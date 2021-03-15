package com.forecast.weather.ui.map

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.forecast.weather.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Maps : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private lateinit var mapsViewModel: MapsViewModel
    lateinit var btnAdd : FloatingActionButton

    var place : MutableLiveData<LatLng> = MutableLiveData<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide actionBar
        val actionBar = supportActionBar
        actionBar?.hide()

        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapsViewModel=ViewModelProvider(this).get(MapsViewModel::class.java)
        btnAdd=findViewById(R.id.btnDone)

        val shared= PreferenceManager.getDefaultSharedPreferences(this)
        val fromSetting = intent.getBooleanExtra("MAP",false)

        btnAdd.setOnClickListener {
            if(place.value?.latitude != null){
                CoroutineScope(Dispatchers.IO).launch {
                    mapsViewModel.insertOneWeather(place.value!!.latitude.toString(), place.value!!.longitude.toString())
                    Log.i("hh", "${place.value!!.latitude} ${place.value!!.longitude}")
                    if (fromSetting){
                        val editor = shared.edit()
                        editor.putString("mapLat",place.value!!.latitude.toString() )
                        editor.putString("mapLong", place.value!!.longitude.toString())
                        editor.apply()

                    }
                }
            }
            else
            {
                Toast.makeText(it.context,getString(R.string.youHave), Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //get destination location when user click on map
        mMap.setOnMapClickListener { latLng ->
            place.value = latLng
            println(place)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(place.value!!).title("Marker in $place.value!!"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place.value!!))

        }
        //to make zoom in egypt in the beginning
        val cairo = LatLng(30.033333, 31.233334)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cairo, 5F));

    }

}