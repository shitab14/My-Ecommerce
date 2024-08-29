package com.mir.myecommerce.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mir.commonlibrary.LocationUtil
import com.mir.commonlibrary.PermissionManager
import com.mir.commonlibrary.RestartHelper
import com.mir.commonlibrary.base.BaseActivity
import com.mir.commonlibrary.imagelib.ImageUtil
import com.mir.commonlibrary.ui.UIUtil
import com.mir.myecommerce.R
import com.mir.myecommerce.databinding.ActivityMainBinding
import com.mir.myecommerce.presentation.listpage.ListActivity
import com.mir.testermodule.presentation.DynamicViewActivity
import com.mir.testermodule.presentation.login.LoginActivity
import com.mir.testermodule.presentation.sliderpage.SliderActivity
import com.mir.testermodule.presentation.speechmatchpage.SpeechMatchActivity
import com.mir.testermodule.presentation.threadoperation.ThreadOperationActivity
import com.mir.testermodule.presentation.videopage.VideoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.io.File
import java.io.IOException

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


    private var imageFile: File? = null
    private var imageUri: Uri? = null

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
                UIUtil.showToast(this,
                    getString(R.string.text_all_permissions_granted))
                // Proceed with the app functionality that requires the permissions
            } else {
                UIUtil.showToast(this,
                    getString(R.string.text_some_permissions_are_denied))
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

        if (requestCode == PermissionManager.PERMISSION_LOCATION_REQUEST_CODE) {
            locationTracker.onRequestPermissionsResult(requestCode, permissions, grantResults)
            permissionManager.handlePermissionsResult(requestCode, permissions, grantResults)
        }
        if (requestCode == PermissionManager.PERMISSION_FOR_BOTH_CAMERA_WRITE_ACCESS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (permissionManager.checkAndRequestPermissionsForCamera()) {
                    val values = ContentValues()
                    values.put(MediaStore.Images.Media.TITLE, "New Picture")
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
                    imageUri = contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
                    )
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    startActivityForResult(intent, PermissionManager.REQUEST_FOR_CAMERA)
                } else {
                    UIUtil.showToast(this, "ERROR1")
                }
            } else {
                UIUtil.showToast(this, "ERROR2")
            }
        }
        if (requestCode == PermissionManager.PERMISSION_FOR_WRITE_ACCESS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ImageUtil.getImageFromGallery(this)
            } else {
                UIUtil.showToast(this, "ERROR3")
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
    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun goToSliderActivity() {
        val intent = Intent(this, SliderActivity::class.java)
        startActivity(intent)
    }

    private fun goToVideoActivity() {
        val intent = Intent(this, VideoActivity::class.java)
        startActivity(intent)
    }

    private fun goToThreadOperationActivity() {
        val intent = Intent(this, ThreadOperationActivity::class.java)
        startActivity(intent)
    }

    private fun goToSpeechMatchActivity() {
        val intent = Intent(this, SpeechMatchActivity::class.java)
        startActivity(intent)
    }

    // Observers & Listeners
    private fun setupLiveDataObservers() {
        viewModel.internetConnected.observe(this) { connected ->
            if(connected) {
                binding.tvInternet.text = resources.getText(R.string.internet_connection_exists) //"Connection Exists"
                binding.tvInternet.setTextColor(getColor(android.R.color.holo_green_dark))
            } else {
                binding.tvInternet.text = resources.getText(R.string.internet_connection_missing) //"Connection Missing"
                binding.tvInternet.setTextColor(getColor(android.R.color.holo_red_dark))
            }
        }
        viewModel.message.observe(this) { msg ->
            UIUtil.showToast(this, msg)
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
            UIUtil.showToast(this, resources.getText(R.string.text_language_switched).toString())
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
            UIUtil.showToast(this, "Set to DB")
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

        binding.btnGoToLoginActivity.setOnClickListener {
            goToLoginActivity()
        }

        binding.btnGoToVideoActivity.setOnClickListener {
            goToVideoActivity()
        }

        binding.btnGoToSliderActivity.setOnClickListener {
            goToSliderActivity()
        }

        binding.btnThreadOperationActivity.setOnClickListener {
            goToThreadOperationActivity()
        }

        binding.btnSpeechMatchActivity.setOnClickListener {
            goToSpeechMatchActivity()
        }

        binding.btnGetImageFromGallery.setOnClickListener {
            ImageUtil.getImageFromGallery(this)
        }

        binding.btnGetImageFromCamera.setOnClickListener {
            getImageFromCamera()
        }

    }

    // Get Image
    fun openGalleryAndSelect(data: Intent) {
        var selectedImageUri: Uri? = data.data
        try {
            var bitmap =
                MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    selectedImageUri
                )
            bitmap = ImageUtil.resize(bitmap, 600, 600)
            selectedImageUri = ImageUtil.getImageUri(this, bitmap)
            if (selectedImageUri != null) {
                val filePathToUpload = ImageUtil.getRealPathFromURI(selectedImageUri, this)
                val finalFile = File(ImageUtil.getRealPathFromURI(selectedImageUri, this)) // File

                saveImages(finalFile, filePathToUpload, bitmap)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun openCameraAndShoot() {
        try {
            if (permissionManager.checkAndRequestPermissionsForCamera()) {
                var bitmapImage: Bitmap = MediaStore.Images.Media.getBitmap(
                    contentResolver, imageUri
                )
                bitmapImage = ImageUtil.resize(bitmapImage, 600, 600)
                val tempUri: Uri? = ImageUtil.getImageUri(this, bitmapImage)

                val finalFile = File(ImageUtil.getRealPathFromURI(tempUri, this)) // File
                val filePathToUpload = finalFile.toString() // File Path
                saveImages(finalFile, filePathToUpload, bitmapImage)
            } else {
                UIUtil.showToast(this, "ERROR4")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // On Activity Result
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PermissionManager.REQUEST_FOR_CAMERA ->
                if (resultCode == RESULT_OK && data != null) {
                    openCameraAndShoot()
                }

            PermissionManager.REQUEST_FOR_WRITE_INTERNAL ->
                if (resultCode == RESULT_OK && data != null) {
                    openGalleryAndSelect(data)
                }
        }

    }

    private fun saveImages(file: File, filePathToUpload: String, bitmap: Bitmap) {
        imageFile = File(filePathToUpload)
        binding.ivSetImage.setImageBitmap(bitmap)
        UIUtil.showToast(this, "SAVED!")
    }

    fun getImageFromCamera() {
        if (permissionManager.checkAndRequestPermissionsForCamera()) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New Picture")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
            imageUri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

            startActivityForResult(intent, PermissionManager.REQUEST_FOR_CAMERA)
        }
    }

}