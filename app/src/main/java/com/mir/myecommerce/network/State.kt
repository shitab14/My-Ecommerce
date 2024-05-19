package com.mir.myecommerce.network

/**
Created by Shitab Mir on 19/5/24.
shitabmir@gmail.com
 **/
sealed class State<out T> {
 data class Success<T>(val data: T) : State<T>()
 data class Failed(val message: String, val code: Int? = null) : State<Nothing>()
 object Loading : State<Nothing>()
}