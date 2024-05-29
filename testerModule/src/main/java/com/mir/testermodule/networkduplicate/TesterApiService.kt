package com.mir.testermodule.networkduplicate

import com.mir.testermodule.data.dynamicviewdto.DynamicViewPageResponse
import retrofit2.Response
import retrofit2.http.GET

/**
Created by Shitab Mir on 19/5/24.
shitabmir@gmail.com
 **/
interface TesterApiService {
    @GET("jsons/dynamicview.json")
    suspend fun getDynamicViewData(): Response<DynamicViewPageResponse>

}