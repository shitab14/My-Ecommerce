package com.mir.commonlibrary

import android.content.Context
import android.net.ConnectivityManager

/**
Created by Shitab Mir on 8/5/24.
shitabmir@gmail.com
 **/
object NetworkUtil {

 private val connectivityManager: ConnectivityManager by lazy {
  val context = ApplicationContextProvider.getContext()
  if (context != null) {
   context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  } else {
   throw IllegalStateException("Application context not set!")
  }
 }

 fun isNetworkAvailable(): Boolean {
  val networkInfo = connectivityManager.activeNetworkInfo
  return networkInfo != null && networkInfo.isConnected
 }
}