package com.mir.myecommerce.presentation.listpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mir.commonlibrary.base.BaseViewModel
import com.mir.myecommerce.domain.pokemonlistusecase.DataSourceForPagination
import com.mir.testermodule.networkduplicate.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Created by Shitab Mir on 24/5/24.
shitabmir@gmail.com
 **/
@HiltViewModel
class ListActivityViewModel @Inject constructor(private val dataSource: DataSourceForPagination)
    : BaseViewModel() {

    private val tag = "ListActivityVM:"

    val items: LiveData<List<PokemonItem>> = dataSource.getItems()
    private var isPaginationLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    fun isPaginationLoading(): LiveData<Boolean> = isPaginationLoading

    fun loadMoreItems() {
        viewModelScope.launch {
            dataSource.invoke().collect{
                when (it) {
                    is State.Loading -> {
                        Log.d(tag,"State.Loading")
                        isPaginationLoading.value = true
                    }
                    is State.Success -> {
                        Log.d(tag,"State.Success ${it.data.results}")
                        isPaginationLoading.value = false
                    }
                    is State.Failed -> {
                        Log.d(tag,"State.Failed")
                        isPaginationLoading.value = false
                    }

                }
            }
        }
    }

    init {
        Log.d(tag, "ListActivityViewModel Created")
    }
}