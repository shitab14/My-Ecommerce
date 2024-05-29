package com.mir.testermodule.data.repository

import com.mir.testermodule.networkduplicate.TesterApiService
import com.mir.testermodule.data.dynamicviewdto.DynamicViewPageResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

/**
Created by Shitab Mir on 29/5/24.
shitabmir@gmail.com
 **/
class DynamicViewPageRepositoryImp @Inject constructor(
 @Named("tester_app_api_service") private val apiClient: TesterApiService
) :DynamicViewPageRepository {
 override suspend fun getDynamicViewResponse(): Response<DynamicViewPageResponse> {
  return apiClient.getDynamicViewData()
 }

}