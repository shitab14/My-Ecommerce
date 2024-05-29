package com.mir.testermodule.di

import android.content.Context
import com.mir.testermodule.networkduplicate.TesterApiService
import com.mir.testermodule.data.repository.DynamicViewPageRepository
import com.mir.testermodule.data.repository.DynamicViewPageRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
Created by Shitab Mir on 29/5/24.
shitabmir@gmail.com
 **/

@Module
@InstallIn(SingletonComponent::class)
class DynamicViewDIModule {
 @Provides
 @Singleton
 fun provideDynamicViewRepository(
  @ApplicationContext context: Context,
  @Named("tester_app_api_service") testerApiService: TesterApiService
 ): DynamicViewPageRepository {
  return DynamicViewPageRepositoryImp(testerApiService)
 }
}