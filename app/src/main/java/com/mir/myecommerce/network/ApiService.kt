package com.mir.myecommerce.network

import com.mir.myecommerce.data.datamodel.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
Created by Shitab Mir on 19/5/24.
shitabmir@gmail.com
 **/
interface ApiService {
    @GET("jsons/{path}")
    suspend fun getResponseData(@Path("path") path: String): Response<ResponseData>

}