package com.mir.myecommerce.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mir.myecommerce.common.NetworkUtil

/**
Created by Shitab Mir on 24/5/24.
shitabmir@gmail.com
 **/
abstract class BaseViewModel: ViewModel() {

    private val baseViewModelTag: String = "Tag:BaseVM:"

    private val _internetConnected : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val internetConnected: LiveData<Boolean> get() = _internetConnected

    fun checkInternetConnection() {
        _internetConnected.value = NetworkUtil.isNetworkAvailable()
    }

    override fun onCleared() {
        Log.d(baseViewModelTag, "BaseVM: onCleared")
        super.onCleared()
    }

}