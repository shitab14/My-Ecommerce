package com.mir.myecommerce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mir.myecommerce.common.NetworkUtil
import com.mir.myecommerce.domain.AppUseCase
import com.mir.myecommerce.network.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Created by Shitab Mir on 8/5/24.
shitabmir@gmail.com
 **/
@HiltViewModel
class MainViewModel  @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    private val _internetConnected : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val internetConnected: LiveData<Boolean> get() = _internetConnected

    fun checkInternetConnection() {
        _internetConnected.value = NetworkUtil.isNetworkAvailable()
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