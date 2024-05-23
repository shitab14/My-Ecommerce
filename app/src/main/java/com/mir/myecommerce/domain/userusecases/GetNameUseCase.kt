package com.mir.myecommerce.domain.userusecases

import com.mir.cachemodule.sharedpreference.SharedPreferenceConstants
import com.mir.cachemodule.sharedpreference.SharedPreferenceManager
import javax.inject.Inject

/**
Created by Shitab Mir on 21/5/24.
shitabmir@gmail.com
 **/
class GetNameUseCase @Inject constructor(private val sharedPreferenceManager: SharedPreferenceManager) {

 operator fun invoke() : String{
  return sharedPreferenceManager.get(SharedPreferenceConstants.USER_NAME_KEY, "").toString()
 }


}