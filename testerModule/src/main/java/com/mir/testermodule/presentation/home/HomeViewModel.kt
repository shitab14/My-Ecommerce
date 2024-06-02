package com.mir.testermodule.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mir.testermodule.DateUtil
import com.mir.testermodule.data.dynamicviewdto.DynamicViewPageResponse
import com.mir.testermodule.domain.GetDynamicViewUseCase
import com.mir.testermodule.networkduplicate.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Created by Shitab Mir on 28/5/24.
shitabmir@gmail.com
 **/
@HiltViewModel
class HomeViewModel @Inject constructor(
 private val useCase: GetDynamicViewUseCase
) : ViewModel() {

 private val _response = MutableLiveData<DynamicViewPageResponse>()
 val response: LiveData<DynamicViewPageResponse> get() = _response

 private val _message = MutableLiveData<String>()
 val message: LiveData<String> = _message
 fun fetchData() {

  viewModelScope.launch {
   useCase.invoke().collect {
    when (it) {
     is State.Loading -> {
      _message.value = "LOADING!!!"
     }
     is State.Success -> {
      _message.value = "Data Received"
      _response.value = it.data
      HomePageCache.homePageDataCache = it.data
      HomePageCache.lastCacheTimestamp = DateUtil.getCurrentTimeInMillis()

     }
     is State.Failed -> {
      _message.value = "FAILED!!!"
     }

    }
   }
  }

 }

}