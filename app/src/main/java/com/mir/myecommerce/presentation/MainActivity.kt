package com.mir.myecommerce.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mir.myecommerce.R
import com.mir.myecommerce.base.BaseActivity
import com.mir.myecommerce.common.LocationUtil
import com.mir.myecommerce.common.PermissionManager
import com.mir.myecommerce.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var viewModel: MainViewModel

    private lateinit var fadeAnimation: Animation

    private lateinit var locationTracker: LocationUtil

    private lateinit var permissionManager: PermissionManager
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

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

        locationTracker = LocationUtil(this)
        setupPermissionManager()
        requestMyPermissions()
    }

    private fun requestMyPermissions() {
        permissionManager.requestPermissions(permissions)  { allPermissionsGranted ->
            if (allPermissionsGranted) {
                Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
                // Proceed with the app functionality that requires the permissions
            } else {
                Toast.makeText(this, "Some permissions are denied", Toast.LENGTH_SHORT).show()
                // Handle the case where permissions are denied
            }
        }
    }

    private fun setupPermissionManager() {
        permissionManager = PermissionManager(this)

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

        permissionManager.handlePermissionsResult(requestCode, permissions, grantResults)

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

        viewModel.userListFromDB.observe(this) { userList ->
            var allUsersData = ""
            if (!(userList.isNullOrEmpty())) {
                userList.forEach { user ->
                    allUsersData += "Age: ${user.age} Name: ${user.lastName}, ${user.firstName}\n"
                }
                binding.tvUsersDataFromDB.text = allUsersData
            } else {
                binding.tvUsersDataFromDB.text = "No Data"
            }
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

        binding.btnSetToPref.setOnClickListener {
            if (binding.etSetNameToPref.text.isNotBlank()) {
                viewModel.setNameToSharedPreference(binding.etSetNameToPref.text.toString())
            }
        }

        binding.btnGetFromPref.setOnClickListener {
            if (viewModel.getNameFromSharedPreference().isNotBlank()) {
                binding.tvGetNameFromPref.text = viewModel.getNameFromSharedPreference()
            }
        }

        binding.btnInsertUserToDB.setOnClickListener {
            var age: Int? = null
            if (!binding.etSetUserAgeDatabase.text.isNullOrBlank()) {
                age = try {
                    binding.etSetUserAgeDatabase.text.toString().toInt()
                } catch (e: Exception) {
                    null
                }
            }
            viewModel.insertUserToDatabase(
                fName = (binding.etSetUserFirstNameDatabase.text ?: "").toString(),
                lName = (binding.etSetUserLastNameDatabase.text ?: "").toString(),
                age =  age
            )
            Toast.makeText(this, "Set to DB", Toast.LENGTH_LONG).show()
            binding.etSetUserFirstNameDatabase.text.clear()
            binding.etSetUserLastNameDatabase.text.clear()
            binding.etSetUserAgeDatabase.text.clear()
        }

        binding.btnFetchUsersFromDB.setOnClickListener {
            lifecycleScope.launch {
                viewModel.fetchUsersFromDatabase()
            }
        }

        binding.btnClearDB.setOnClickListener {
            viewModel.clearUserDatabase()
        }

    }

    private fun setupLocationMarkerOnMap(latitude: Double, longitude: Double) {
        if (binding.mapView.isVisible) {
            val point = GeoPoint(latitude, longitude)

            val startMarker = Marker(binding.mapView)
            startMarker.position = point
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            binding.mapView.overlays.add(startMarker)
            binding.mapView.controller.setCenter(point)
        }
    }




}