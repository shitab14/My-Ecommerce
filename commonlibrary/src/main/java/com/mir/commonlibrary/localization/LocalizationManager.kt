package com.mir.commonlibrary.localization

import android.content.Context
import android.os.Build
import java.util.Locale

/**
Created by Shitab Mir on 24/5/24.
shitabmir@gmail.com
 **/

object LocalizationManager {
 fun setLocale(context: Context, language: String, country: String) {
  val locale = Locale(language, country)
  Locale.setDefault(locale)

  val resources = context.resources
  val config = resources.configuration
  config.setLocale(locale)

//  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
   config.setLayoutDirection(locale)
//  }

  resources.updateConfiguration(config, resources.displayMetrics)

  // Update application context
  context.applicationContext.createConfigurationContext(config)
 }
}
// eg. LocalizationManager.setLocale(this, "en", "BD")
