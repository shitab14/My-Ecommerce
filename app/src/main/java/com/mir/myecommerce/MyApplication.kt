package com.mir.myecommerce

import android.app.Application
import com.mir.myecommerce.common.ApplicationContextProvider

/**
Created by Shitab Mir on 8/5/24.
shitabmir@gmail.com
 **/
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationContextProvider.setContext(this)  // Set the context in Application's onCreate
    }

}