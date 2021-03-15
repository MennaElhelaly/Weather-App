package com.forecast.weather.ui.mainActivity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.forecast.weather.R
import com.forecast.weather.dataLayer.SharedPreference
import com.forecast.weather.ui.setting.MyLocation
import com.forecast.weather.ui.setting.setLocale
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var PERMISSION_ID = 55
    private lateinit var mylocation : MyLocation
    private lateinit var sharedObj : SharedPreference

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide actionBar
        val actionBar = supportActionBar
        actionBar?.hide()
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.setting
                )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val shared= PreferenceManager.getDefaultSharedPreferences(this)
        shared.getString("lat", "30.033333")
        shared.getString("long", "31.233334")

        sharedObj= SharedPreference(this)
        if (sharedObj.language.equals("ar"))
        { setLocale(this,"ar") }
        else {
            setLocale(this,"en")
        }

        //getLocation
        mylocation= MyLocation(this)
        mylocation.getLastLocation()
        mylocation.location.observe(this,{
            if (it.latitude==null)
            {
                Log.i("hh","null from main")
            }
            else{
                val editor = shared.edit()
                editor.putString("lat",it.latitude.toString() )
                editor.putString("long", it.longitude.toString())
                editor.apply()
            }

        })
    }
    //call back method after first pop up to know if he accept
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String?>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mylocation.getLastLocation()
            } else {
                Toast.makeText(
                        this,
                        getString(R.string.reqDeni)+"\n"+getString(R.string.accept),
                        Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}