package com.mir.myecommerce.common

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
Created by Shitab Mir on 20/5/24.
shitabmir@gmail.com
 **/

class PermissionManager(private val activity: Activity) {

 /*
 Implementation:

     private lateinit var permissionManager: PermissionManager

     permissionManager = PermissionManager(this)

     val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION
            ...
        )

     // Request permissions with a callback
        permissionManager.requestPermissions(permissions) { allPermissionsGranted ->
            if (allPermissionsGranted) {
                Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
                // Proceed with the app functionality that requires the permissions
            } else {
                Toast.makeText(this, "Some permissions are denied", Toast.LENGTH_SHORT).show()
                // Handle the case where permissions are denied
            }
        }

        ...
        ...

     override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.handlePermissionsResult(requestCode, permissions, grantResults)
    }

  */


 companion object {
  const val PERMISSION_REQUEST_CODE = 1
 }

 private var permissionResultCallback: ((Boolean) -> Unit)? = null

 // Function to request multiple permissions with a callback
 fun requestPermissions(permissions: Array<String>, callback: (Boolean) -> Unit) {
  permissionResultCallback = callback

  val permissionsToRequest = permissions.filter {
   ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
  }

  if (permissionsToRequest.isNotEmpty()) {
   ActivityCompat.requestPermissions(activity, permissionsToRequest.toTypedArray(), PERMISSION_REQUEST_CODE)
  } else {
   // All permissions are already granted
   callback(true)
  }
 }

 // Function to handle permission results
 fun handlePermissionsResult(
  requestCode: Int,
  permissions: Array<out String>,
  grantResults: IntArray
 ) {
  if (requestCode == PERMISSION_REQUEST_CODE) {
   val allPermissionsGranted = grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }
   permissionResultCallback?.invoke(allPermissionsGranted)
  }
 }

 // Utility function to check if a specific permission is granted
 fun isPermissionGranted(permission: String): Boolean {
  return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
 }
}