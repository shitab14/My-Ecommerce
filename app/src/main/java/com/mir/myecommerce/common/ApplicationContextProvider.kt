package com.mir.myecommerce.common

import android.content.Context

/**
Created by Shitab Mir on 8/5/24.
shitabmir@gmail.com
 **/
object ApplicationContextProvider {

    private var appContext: Context? = null

    fun setContext(context: Context) {
        appContext = context.applicationContext // Use applicationContext for safety
    }

    fun getContext(): Context? {
        return appContext
    }
}