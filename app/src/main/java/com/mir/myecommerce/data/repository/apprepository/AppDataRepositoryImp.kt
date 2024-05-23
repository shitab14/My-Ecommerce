package com.mir.myecommerce.data.repository.apprepository

import com.mir.myecommerce.data.datamodel.ResponseData
import com.mir.myecommerce.network.ApiService
import retrofit2.Response
import javax.inject.Inject

/**
Created by Shitab Mir on 19/5/24.
shitabmir@gmail.com
 **/
class AppDataRepositoryImp @Inject constructor(private val apiClient: ApiService) :
    AppDataRepository {
    override suspend fun getSomethingIncoming(path: String): Response<ResponseData> {
        return apiClient.getResponseData(path)
    }

    override fun getSomething(): String {
        return "simpledata.json"
    }

}