package com.mir.cachemodule.sharedpreference

import android.content.SharedPreferences

/**
Created by Shitab Mir on 21/5/24.
shitabmir@gmail.com
 **/
class SharedPreferenceManager(val sharedPreferences: SharedPreferences?) {

 // Generic getter for different data types
 inline fun <reified T> get(key: String, defaultValue: T): Any {

  if (sharedPreferences != null) {

   with(sharedPreferences) {
    when (T::class.java) {
     String::class.java -> return getString(key, defaultValue as String) ?: ""
     Int::class.java -> return getInt(key, defaultValue as Int)
     Boolean::class.java -> return getBoolean(key, defaultValue as Boolean)
     Float::class.java -> return getFloat(key, defaultValue as Float)
     else -> throw IllegalArgumentException("Unsupported data type")
    }
   }
  } else {
   return ""
  }
 }

 // Generic setter for different data types
 fun <T> set(key: String, value: T) {
  sharedPreferences?.edit()?.apply {
   when (value) {
    is String -> putString(key, value)
    is Int -> putInt(key, value)
    is Boolean -> putBoolean(key, value)
    is Float -> putFloat(key, value)
    else -> throw IllegalArgumentException("Unsupported data type")
   }
  }?.apply()
 }

 // Helper function to remove a preference
 fun remove(key: String) {
  sharedPreferences?.edit()?.remove(key)?.apply()
 }
}