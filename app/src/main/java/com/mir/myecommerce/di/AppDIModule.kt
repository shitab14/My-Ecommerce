package com.mir.myecommerce.di

import android.content.Context
import com.mir.myecommerce.data.repository.AppDataRepository
import com.mir.myecommerce.data.repository.AppDataRepositoryImp
import com.mir.myecommerce.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
Created by Shitab Mir on 19/5/24.
shitabmir@gmail.com
 **/

@Module
@InstallIn(SingletonComponent::class)
class AppDIModule {
 @Provides
 @Singleton
 fun provideAppDataRepository(
  @ApplicationContext context: Context,
  apiService: ApiService
 ): AppDataRepository {
  return AppDataRepositoryImp(apiService)
 }
}