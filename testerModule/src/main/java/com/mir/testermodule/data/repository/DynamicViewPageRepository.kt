package com.mir.testermodule.data.repository

import com.mir.testermodule.data.dynamicviewdto.DynamicViewPageResponse
import retrofit2.Response

/**
Created by Shitab Mir on 29/5/24.
shitabmir@gmail.com
 **/
interface DynamicViewPageRepository {
 suspend fun getDynamicViewResponse(): Response<DynamicViewPageResponse>

}