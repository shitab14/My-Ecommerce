package com.mir.myecommerce.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
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
import com.mir.myecommerce.common.RestartHelper
import com.mir.myecommerce.databinding.ActivityMainBinding
import com.mir.myecommerce.presentation.listpage.ListActivity
import com.mir.testermodule.presentation.DynamicViewActivity
import dagger.hilt.android.AndroidEntryPoint
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

    // Lifecycle
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

    override fun onResume() {
        super.onResume()
        viewModel.checkInternetConnection()
    }

    override fun onStop() {
        super.onStop()
        locationTracker.stopLocationUpdates()
    }

    // Permission
    private fun setupPermissionManager() {
        permissionManager = PermissionManager(this)
    }
    private fun requestMyPermissions() {
        permissionManager.requestPermissions(permissions)  { allPermissionsGranted ->
            if (allPermissionsGranted) {
                Toast.makeText(this,
                    getString(R.string.text_all_permissions_granted), Toast.LENGTH_SHORT).show()
                // Proceed with the app functionality that requires the permissions
            } else {
                Toast.makeText(this,
                    getString(R.string.text_some_permissions_are_denied), Toast.LENGTH_SHORT).show()
                // Handle the case where permissions are denied
            }
        }
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

    private fun restartActivity() {
        RestartHelper.restartApp(this)
//        val intent = intent
//        finish()
//        startActivity(intent)
        overridePendingTransition(0, 0) // Optional: No animation on restart
    }

    private fun goToListActivity() {
        val data: String
        var bundle = Bundle()
        if(binding.etWillTakeThisToNext.text.isNotBlank()) {
            data = binding.etWillTakeThisToNext.text.toString()
//            intent.putExtra("EXTRA_STRING_DATA", data)
            bundle = Bundle().apply {
                putString("EXTRA_BUNDLE_STRING_DATA", data)
                putInt("EXTRA_BUNDLE_INT_DATA", 1)
            }
        }
        val intent = Intent(this, ListActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }
    private fun goToDynamicViewActivity() {
        val data: String
        var bundle = Bundle()
        if(binding.etWillTakeThisToNext.text.isNotBlank()) {
            data = binding.etWillTakeThisToNext.text.toString()
//            intent.putExtra("EXTRA_STRING_DATA", data)
            bundle = Bundle().apply {
                putString("EXTRA_BUNDLE_STRING_DATA", data)
                putInt("EXTRA_BUNDLE_INT_DATA", 1)
            }
        }
        val intent = Intent(this, DynamicViewActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }

    // Observers & Listeners
    private fun setupLiveDataObservers() {
        viewModel.internetConnected.observe(this) { connected ->
            if(connected) {
                binding.tvInternet.text = resources.getText(R.string.internet_connection_exists) //"Connection Exists"
                binding.tvInternet.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            } else {
                binding.tvInternet.text = resources.getText(R.string.internet_connection_missing) //"Connection Missing"
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
                binding.tvUsersDataFromDB.text = resources.getText(R.string.text_no_data) //"No Data"
            }
        }
    }

    private fun setupClickListeners() {
        binding.iBtnLanguageSwitch.setOnClickListener {
            viewModel.switchLanguage(this)
            Toast.makeText(this, resources.getText(R.string.text_language_switched), Toast.LENGTH_LONG).show()
            restartActivity()
        }

        binding.iBtnFetchLocation.setOnClickListener {
            if (locationTracker.hasLocationPermission()) {
                locationTracker.startLocationUpdates { location ->
                    // Update UI or perform actions with the location
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.d("Location", "Latitude: $latitude, Longitude: $longitude")
                    // You can update a TextView, send to server, etc.
                    @SuppressLint("SetTextI18n")
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

        binding.btnGoToListActivity.setOnClickListener {
            goToListActivity()
        }

        binding.btnGoToDynamicViewActivity.setOnClickListener {
            goToDynamicViewActivity()
        }

    }

}