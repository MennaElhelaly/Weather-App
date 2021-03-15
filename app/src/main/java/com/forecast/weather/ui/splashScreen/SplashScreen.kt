package com.forecast.weather.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.forecast.weather.ui.mainActivity.MainActivity
import com.forecast.weather.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar?.hide()

        setContentView(R.layout.activity_splash_screen)
        rain.alpha=0f
        rain.animate().setDuration(1500).alpha(1f).withEndAction {
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }


    }
}