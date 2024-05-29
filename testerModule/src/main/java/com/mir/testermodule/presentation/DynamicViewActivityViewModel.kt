package com.mir.testermodule.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
Created by Shitab Mir on 28/5/24.
shitabmir@gmail.com
 **/
class DynamicViewActivityViewModel : ViewModel() {
 private val _selectedTab = MutableLiveData<Int>()
 val selectedTab: LiveData<Int> get() = _selectedTab

 fun selectTab(tab: Int) {
  _selectedTab.value = tab
 }
}