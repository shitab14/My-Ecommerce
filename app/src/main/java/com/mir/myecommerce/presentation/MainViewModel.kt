package com.mir.myecommerce.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mir.myecommerce.common.NetworkUtil
import com.mir.myecommerce.domain.appusecases.AppUseCase
import com.mir.myecommerce.domain.userusecases.GetNameUseCase
import com.mir.myecommerce.domain.userusecases.SetNameUseCase
import com.mir.myecommerce.network.State
import com.mir.testermodule.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Created by Shitab Mir on 8/5/24.
shitabmir@gmail.com
 **/
@HiltViewModel
class MainViewModel  @Inject constructor(
    private val appUseCase: AppUseCase,
    private val setNameUseCase: SetNameUseCase,
    private val getNameUseCase: GetNameUseCase,
) : ViewModel() {
    private val _internetConnected : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val internetConnected: LiveData<Boolean> get() = _internetConnected

    fun checkInternetConnection() {
        _internetConnected.value = NetworkUtil.isNetworkAvailable()
    }

    fun getCurrentTimeInMillis(): Long {
        return DateUtil.getCurrentTimeInMillis()
    }

    fun setNameToSharedPreference(name: String) {
        setNameUseCase.invoke(name = name)
    }
    fun getNameFromSharedPreference(): String {
        return getNameUseCase.invoke()
    }

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    init {
        viewModelScope.launch {
            appUseCase.invoke("").collect{
                when (it) {
                    is State.Loading -> {
                        _message.value = "LOADING!!!"

                    }
                    is State.Success -> {
                        _message.value = "GOT IT:"+it.data.data

                    }
                    is State.Failed -> {
                        _message.value = "FAILED!!!"
                    }

                }

            }

        }
    }

}