package com.mir.myecommerce.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mir.cachemodule.database.UserTable
import com.mir.myecommerce.base.BaseViewModel
import com.mir.myecommerce.common.NetworkUtil
import com.mir.myecommerce.domain.appusecases.AppUseCase
import com.mir.myecommerce.domain.databaseuserusecase.DeleteUserTableUseCase
import com.mir.myecommerce.domain.databaseuserusecase.FetchUsersUseCase
import com.mir.myecommerce.domain.databaseuserusecase.InsertUserUseCase
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
    private val insertUserUseCase: InsertUserUseCase,
    private val fetchUserUseCase: FetchUsersUseCase,
    private val deleteUserTableUseCase: DeleteUserTableUseCase,
) : BaseViewModel() {

    fun getCurrentTimeInMillis(): Long {
        return DateUtil.getCurrentTimeInMillis()
    }

    fun setNameToSharedPreference(name: String) {
        setNameUseCase.invoke(name = name)
    }
    fun getNameFromSharedPreference(): String {
        return getNameUseCase.invoke()
    }

    private val _userListFromDB : MutableLiveData<List<UserTable>> = MutableLiveData<List<UserTable>>()
    val userListFromDB: LiveData<List<UserTable>> get() = _userListFromDB
    suspend fun fetchUsersFromDatabase() {
        viewModelScope.launch {
            _userListFromDB.value = fetchUserUseCase.invoke()
//        return fetchUserUseCase.invoke()
        }
    }
    fun insertUserToDatabase(fName: String, lName: String, age: Int?) {
        val user = UserTable(
            id = 0,
            firstName = fName,
            lastName = lName,
            age = age ?: 0,
        )
        viewModelScope.launch {
            insertUserUseCase.invoke(
                user = user
            )
        }
    }

    fun clearUserDatabase() {
        viewModelScope.launch {
            deleteUserTableUseCase.invoke()
        }
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