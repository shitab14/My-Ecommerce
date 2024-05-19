package com.mir.myecommerce.domain

import retrofit2.Response

/**
Created by Shitab Mir on 19/5/24.
shitabmir@gmail.com
 **/
interface AppDataRepository {
    suspend fun getSomethingIncoming(path: String): Response<ResponseData>

    fun getSomething(): String
}