package com.mir.myecommerce.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mir.cachemodule.sharedpreference.SharedPreferenceConstants
import com.mir.cachemodule.sharedpreference.SharedPreferenceManager
import com.mir.myecommerce.common.localization.LocalizationManager
import com.mir.myecommerce.common.NetworkUtil
import javax.inject.Inject

/**
Created by Shitab Mir on 24/5/24.
shitabmir@gmail.com
 **/
abstract class BaseViewModel: ViewModel() {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private val baseViewModelTag: String = "MyTag:"

    private val _internetConnected : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val internetConnected: LiveData<Boolean> get() = _internetConnected

    fun checkInternetConnection() {
        _internetConnected.value = NetworkUtil.isNetworkAvailable()
    }

    override fun onCleared() {
        Log.d(baseViewModelTag, "BaseVM: onCleared")
        super.onCleared()
    }

    fun switchLanguage(context: Context) {
        if(sharedPreferenceManager
            .get(SharedPreferenceConstants.KEY_APP_LOCALE_LANGUAGE, "en")
            .toString()
            == "en") {
            setLanguageToArabic(context)
        } else {
            setLanguageToEnglish(context)
        }
    }

    private fun setLanguageToArabic(context: Context) {
        sharedPreferenceManager.set(SharedPreferenceConstants.KEY_APP_LOCALE_LANGUAGE, "ar")
        LocalizationManager.setLocale(context, "ar", "BD")
    }

    private fun setLanguageToEnglish(context: Context) {
        sharedPreferenceManager.set(SharedPreferenceConstants.KEY_APP_LOCALE_LANGUAGE, "en")
        LocalizationManager.setLocale(context, "en", "BD")
    }

}