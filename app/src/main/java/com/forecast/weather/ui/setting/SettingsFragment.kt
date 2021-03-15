package com.forecast.weather.ui.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.preference.*
import com.forecast.weather.R
import com.forecast.weather.R.xml
import com.forecast.weather.ui.map.Maps
import kotlinx.coroutines.*

class SettingsFragment : PreferenceFragmentCompat() {
    var changeLanguage=false

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(xml.preferences, rootKey)

        val shared= PreferenceManager.getDefaultSharedPreferences(context)
        val locationMap: SwitchPreferenceCompat?=findPreference("CUSTOM")
        val editor = shared.edit()

        editor.putBoolean("firstTime", true)
        editor.apply()

        locationMap?.onPreferenceClickListener= Preference.OnPreferenceClickListener {
            if(shared.getBoolean("CUSTOM", true))
            {
                Toast.makeText(it.context,getString(R.string.mapOpen), Toast.LENGTH_SHORT).show()
                val intent = Intent(this.context, Maps::class.java)
                intent.putExtra("MAP", true)
                startActivity(intent)
            }
            true
        }

        val language: ListPreference?=findPreference("LANGUAGE_SYSTEM")
        language?.onPreferenceClickListener= Preference.OnPreferenceClickListener {
            changeLanguage=true
            if(shared.getString("LANGUAGE_SYSTEM", "en").equals("en"))
            { setLocale(this.requireActivity(), "ar") }
            else if(shared.getString("LANGUAGE_SYSTEM", "en").equals("ar"))
            { setLocale(this.requireActivity(), "en") }

            true
        }
    }
    override fun onPause() {
        if (changeLanguage){
            CoroutineScope(Dispatchers.Default).launch {
                restart()
            }
            changeLanguage=false
        }
        super.onPause()
    }
    fun restart(){
        val intent = requireActivity().intent
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                or Intent.FLAG_ACTIVITY_NO_ANIMATION)
        requireActivity().overridePendingTransition(0, 0)
        requireActivity().finish()
        requireActivity().overridePendingTransition(0, 0)
        startActivity(intent)
    }

}