package com.mir.commonlibrary

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

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

 fun isVpnActive(context: Context): Boolean {
  val connectivityManager =
   context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

//  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
  val network = connectivityManager.activeNetwork
  val capabilities = connectivityManager.getNetworkCapabilities(network)
  return capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true
//  } else {
  // For devices below API level 23
//   return connectivityManager.allNetworks.any { network ->
//    val networkInfo = connectivityManager.getNetworkInfo(network)
//    networkInfo?.type == ConnectivityManager.TYPE_VPN && networkInfo.isConnected
//   }
//  }
 }

}