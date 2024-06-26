package com.mir.commonlibrary

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.compass.CompassOverlay

/**
Created by Shitab Mir on 12/5/24.
shitabmir@gmail.com
 **/
class LocationUtil(
 private val context: Context,
) {

 private var locationRequest: LocationRequest? = null
 private var locationCallback: LocationCallback? = null
 private var locationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

 // Permission check and request (replace with your permission handling logic)
 fun hasLocationPermission(): Boolean {
  return ActivityCompat.checkSelfPermission(
   context,
   ACCESS_FINE_LOCATION
  ) == PackageManager.PERMISSION_GRANTED
 }

 fun requestLocationPermission() {
  ActivityCompat.requestPermissions(
   (context as Activity),
   arrayOf(ACCESS_FINE_LOCATION),
   LOCATION_PERMISSION_REQUEST_CODE
  )
 }

 fun startLocationUpdates(listener: (location: Location) -> Unit) {
  if (!hasLocationPermission()) {
   requestLocationPermission()
   return
  }

  locationRequest = com.google.android.gms.location.LocationRequest().apply {
   interval = UPDATE_INTERVAL  // Set desired update interval in milliseconds
   fastestInterval = FASTEST_UPDATE_INTERVAL  // Set minimum interval for updates
   priority = PRIORITY_BALANCED_POWER_ACCURACY  // Adjust priority based on your needs
  }

  locationCallback = object : LocationCallback() {
   override fun onLocationResult(locationResult: LocationResult) {
    locationResult.lastLocation?.let { location ->
     listener(location)  // Call the listener with the new location
    }
   }
  }

  if (ActivityCompat.checkSelfPermission(
    context,
    ACCESS_FINE_LOCATION
   ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
    context,
    Manifest.permission.ACCESS_COARSE_LOCATION
   ) != PackageManager.PERMISSION_GRANTED
  ) {
   requestLocationPermission()
   return
  }
  locationProviderClient.requestLocationUpdates(
   locationRequest!!,
   locationCallback!!,
   null  // You can set a Looper for the callback if needed
  )
 }

 fun stopLocationUpdates() {
  locationCallback?.let {
   locationProviderClient.removeLocationUpdates(it)//removeUpdates(it)
   locationCallback = null
  }
 }

 fun onRequestPermissionsResult(
  requestCode: Int,
  permissions: Array<out String>,
  grantResults: IntArray
 ) {

 }

 fun setupMapView(mapView: MapView) {
  mapView.visibility = View.VISIBLE
  mapView.setTileSource(TileSourceFactory.MAPNIK)
  mapView.controller.setZoom(18.0)
  mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
  mapView.setMultiTouchControls(true)

  val compassOverlay = CompassOverlay(context, mapView)
  compassOverlay.enableCompass()
  mapView.overlays.add(compassOverlay)

 }


 companion object {
  private const val LOCATION_PERMISSION_REQUEST_CODE = 100
  private const val UPDATE_INTERVAL = 10000L  // 10 seconds
  private const val FASTEST_UPDATE_INTERVAL = 5000L  // 5 seconds (minimum)
 }
}
