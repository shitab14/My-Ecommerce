package com.mir.testermodule.domain

import android.util.Log
import com.google.gson.Gson
import com.mir.testermodule.networkduplicate.ErrorMessage
import com.mir.testermodule.networkduplicate.State
import com.mir.testermodule.data.dynamicviewdto.DynamicViewPageResponse
import com.mir.testermodule.data.repository.DynamicViewPageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

/**
Created by Shitab Mir on 29/5/24.
shitabmir@gmail.com
 **/
class GetDynamicViewUseCase @Inject constructor(
 private val repository: DynamicViewPageRepository
) {
 operator fun invoke(): Flow<State<DynamicViewPageResponse>> = flow {

  try {
   emit(State.Loading)
   repository.getDynamicViewResponse().let { response ->
    Log.d("GetDynamicViewUseCase", "use case getToken()")
    if (response.isSuccessful) {
     response.body()?.let {
      Log.d("GetDynamicViewUseCase", "Passed ${Gson().toJson(it)}")
      emit(State.Success(it))
     } ?: kotlin.run {
      emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
     }

    } else {
     Log.d("GetDynamicViewUseCase", "failed")
     if (response.code() == 401) {
      emit(State.Failed(ErrorMessage.UNAUTHORIZED_USER, response.code()))
     } else {
      emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
     }
    }

   }
  } catch (e: Exception) {
   Log.d("GetDynamicViewUseCase", "exception")
   e.printStackTrace()
   emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
  }
 }

}