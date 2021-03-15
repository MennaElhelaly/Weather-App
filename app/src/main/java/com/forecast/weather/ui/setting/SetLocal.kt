package com.forecast.weather.ui.setting

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
fun setLocale(activity: Activity, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val resources: Resources = activity.resources
    val config: Configuration = resources.getConfiguration()
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.getDisplayMetrics())
}