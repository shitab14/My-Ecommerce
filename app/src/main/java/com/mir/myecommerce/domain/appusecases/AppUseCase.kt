package com.mir.myecommerce.domain.appusecases

import android.util.Log
import com.google.gson.Gson
import com.mir.myecommerce.data.repository.apprepository.AppDataRepository
import com.mir.myecommerce.data.datamodel.ResponseData
import com.mir.myecommerce.network.State
import com.mir.testermodule.networkduplicate.ErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

/**
Created by Shitab Mir on 19/5/24.
shitabmir@gmail.com
 **/
class AppUseCase @Inject constructor(private val repository: AppDataRepository) {

    operator fun invoke(dummy: String): Flow<State<ResponseData>> = flow {
        try {
            emit(State.Loading)
            repository.getSomething().let { token ->
//                Log.d("AppUseCase", "use case getToken()")
                token.let {
                    val response = repository.getSomethingIncoming(path = it)
                    if(!response.equals(null)) {
                        if (response.isSuccessful) {
                            response.body()?.let { responseData ->
//                            Log.d("AppUseCase", "Passed ${Gson().toJson(responseData)}")
                                emit(State.Success(responseData))
                            } ?: kotlin.run {
                                emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
                            }

                        } else {
//                        Log.d("AppUseCase", "failed")
                            if (response.code() == 401) {
                                emit(State.Failed(ErrorMessage.UNAUTHORIZED_USER, response.code()))
                            } else {
                                emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
                            }
                        }
                    } else {
                        emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
                    }
                } ?: kotlin.run {
                    emit(State.Failed(ErrorMessage.UNAUTHORIZED_USER))
                }
            }
        } catch (e: Exception) {
//            Log.d("AppUseCase", "exception")
            e.printStackTrace()
            emit(State.Failed(ErrorMessage.SOMETHING_WENT_WRONG))
        }
    }
}