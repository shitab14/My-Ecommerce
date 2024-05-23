package com.mir.myecommerce.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

/**
Created by Shitab Mir on 8/5/24.
shitabmir@gmail.com
 **/
abstract class BaseActivity<DataBinding: ViewDataBinding> : AppCompatActivity() {

 var binding: DataBinding by Delegates.notNull()

 private val baseActivityTag: String = "Tag:BaseActivity:"
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)

  Log.d(baseActivityTag, "BaseActivity: onCreate")
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

}