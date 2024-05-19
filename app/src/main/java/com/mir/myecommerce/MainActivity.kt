package com.mir.myecommerce

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.ACCESS_WIFI_STATE
import android.Manifest.permission.INTERNET
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.mir.myecommerce.base.BaseActivity
import com.mir.myecommerce.common.LocationUtil
import com.mir.myecommerce.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
//    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var fadeAnimation: Animation

    private lateinit var locationTracker: LocationUtil

    companion object {
        private const val TAG = "MainActivity:"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupInitialization()
        setupClickListeners()
        setupLiveDataObservers()
        setupAndStartSplashAnimation()

        val locationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationTracker = LocationUtil(this, locationProviderClient)
        setupMapView()
    }


    override fun onStart() {
        super.onStart()
        /*// Auto fetch location
        locationTracker.startLocationUpdates { location ->
            // Update UI or perform actions based on the new location
            Log.d("LocationTracker", "Location update: ${location.latitude}, ${location.longitude}")
        }*/
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkInternetConnection()
    }

    override fun onStop() {
        super.onStop()
        locationTracker.stopLocationUpdates()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionsResult requestCode: $requestCode")

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationTracker.onRequestPermissionsResult(requestCode, permissions, grantResults)


        val permissionsToRequest = ArrayList<String>()
        for (i in grantResults.indices) {
            permissionsToRequest.add(permissions[i])
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray<String>(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    private fun setupLiveDataObservers() {
        viewModel.internetConnected.observe(this) { connected ->
            if(connected) {
                @SuppressLint("SetTextI18n")
                binding.tvInternet.text = "Connection Has"
                binding.tvInternet.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            } else {
                @SuppressLint("SetTextI18n")
                binding.tvInternet.text = "Connection NOT Has"
                binding.tvInternet.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }
        }
        viewModel.message.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupAndStartSplashAnimation() {
        fadeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                // on Animation Start
            }

            override fun onAnimationEnd(p0: Animation?) {
                // on Animation End
                binding.clMainContent.visibility = View.VISIBLE
                binding.clSplashContent.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {
                // on Animation Repeat
            }

        })
        binding.clSplashContent.startAnimation(fadeAnimation)
    }

    private fun setupInitialization() {
        fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in_out)
    }


    @SuppressLint("SetTextI18n")
    private fun setupClickListeners() {
        binding.iBtnLanguageSwitch.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
        }

        binding.iBtnFetchLocation.setOnClickListener {
            if (locationTracker.hasLocationPermission()) {
                locationTracker.startLocationUpdates { location ->
                    // Update UI or perform actions with the location
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.d("Location", "Latitude: $latitude, Longitude: $longitude")
                    // You can update a TextView, send to server, etc.
                    binding.tvLocationCoordinates.text = "Latitude: $latitude, Longitude: $longitude"

                    setupLocationMarkerOnMap(latitude, longitude)
                }
            } else {
                locationTracker.requestLocationPermission()
            }
        }
    }

    private fun setupMapView() {
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK)
        binding.mapView.controller.setZoom(18.0)
        requestPermissionsIfNecessary(
            arrayOf<String>(
//                WRITE_EXTERNAL_STORAGE,
                ACCESS_COARSE_LOCATION,
                ACCESS_NETWORK_STATE,
                ACCESS_WIFI_STATE,
                INTERNET
            )
        )
        binding.mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        binding.mapView.setMultiTouchControls(true)


        val compassOverlay = CompassOverlay(this, binding.mapView)
        compassOverlay.enableCompass()
        binding.mapView.overlays.add(compassOverlay)

    }

    private fun setupLocationMarkerOnMap(latitude: Double, longitude: Double) {
        val point = GeoPoint(latitude, longitude)

        val startMarker = Marker(binding.mapView)
        startMarker.position = point
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        binding.mapView.overlays.add(startMarker)
        binding.mapView.controller.setCenter(point)
    }

    private fun requestPermissionsIfNecessary(permissions: Array<String>) {
        val permissionsToRequest = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(permission)
            }
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray<String>(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1


}