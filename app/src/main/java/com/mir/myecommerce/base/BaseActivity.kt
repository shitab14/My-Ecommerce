package com.mir.myecommerce.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.mir.cachemodule.sharedpreference.SharedPreferenceConstants
import com.mir.cachemodule.sharedpreference.SharedPreferenceManager
import com.mir.myecommerce.common.localization.LocalizationManager
import javax.inject.Inject
import kotlin.properties.Delegates

/**
Created by Shitab Mir on 8/5/24.
shitabmir@gmail.com
 **/
abstract class BaseActivity<DataBinding: ViewDataBinding> : AppCompatActivity() {

 @Inject
 lateinit var sharedPreferenceManager: SharedPreferenceManager

 var binding: DataBinding by Delegates.notNull()

 private val baseActivityTag: String = "MyTag:"
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)

  Log.d(baseActivityTag, "BaseActivity: onCreate")

  // Localization
  try {
   val language: String = getLanguagePreference()
   val country: String = getCountryPreference()
   Log.d(baseActivityTag, "language: $language, country: $country")
   LocalizationManager.setLocale(this, language, country) // Default is en BD
  } catch (e: Exception) {
   Log.e(baseActivityTag, "Exception: $e")
  }

 }

 override fun onStart() {
  super.onStart()
  Log.d(baseActivityTag, "BaseActivity: onStart")
 }

 override fun onResume() {
  super.onResume()
  Log.d(baseActivityTag, "BaseActivity: onResume")
 }

 override fun onPause() {
  super.onPause()
  Log.d(baseActivityTag, "BaseActivity: onPause")
 }

 override fun onStop() {
  super.onStop()
  Log.d(baseActivityTag, "BaseActivity: onStop")
 }

 override fun onSaveInstanceState(outState: Bundle) {
  super.onSaveInstanceState(outState)
  Log.d(baseActivityTag, "BaseActivity: onSaveInstanceState")
 }

 override fun onRestart() {
  super.onRestart()
  Log.d(baseActivityTag, "BaseActivity: onRestart")
 }

 override fun onDestroy() {
  Log.d(baseActivityTag, "BaseActivity: onDestroy")
  super.onDestroy()
 }

 @Deprecated("Deprecated in Java")
 override fun onBackPressed() {
  super.onBackPressed()
  Log.d(baseActivityTag, "BaseActivity: onBackPressed")
 }

 private fun getLanguagePreference(): String {
  return sharedPreferenceManager.get(SharedPreferenceConstants.KEY_APP_LOCALE_LANGUAGE, "en").toString()
 }
 private fun getCountryPreference(): String {
  return sharedPreferenceManager.get(SharedPreferenceConstants.KEY_APP_LOCALE_COUNTRY, "BD").toString()
 }

}