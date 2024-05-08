package com.mir.myecommerce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mir.myecommerce.common.NetworkUtil

/**
Created by Shitab Mir on 8/5/24.
shitabmir@gmail.com
 **/
class MainViewModel : ViewModel() {
    private val _internetConnected : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val internetConnected: LiveData<Boolean> get() = _internetConnected

    fun checkInternetConnection() {
        _internetConnected.value = NetworkUtil.isNetworkAvailable()
    }

}