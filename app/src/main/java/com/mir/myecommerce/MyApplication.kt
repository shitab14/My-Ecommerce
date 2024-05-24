package com.mir.myecommerce

import android.app.Application
import android.util.Log
import com.mir.cachemodule.sharedpreference.SharedPreferenceManager
import com.mir.myecommerce.common.ApplicationContextProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


/**
Created by Shitab Mir on 8/5/24.
shitabmir@gmail.com
 **/
@HiltAndroidApp
class MyApplication: Application() {

    private val myApplicationTag: String = "MyTag:"

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate() {
        super.onCreate()
        Log.d(myApplicationTag, "MyApplication: onCreate")

        ApplicationContextProvider.setContext(this)  // Set the context in Application's onCreate
    }
}