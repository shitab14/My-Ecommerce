package com.mir.myecommerce.di

import android.content.Context
import android.content.SharedPreferences
import com.mir.cachemodule.SharedPreferenceConstants
import com.mir.cachemodule.SharedPreferenceManager
import com.mir.myecommerce.common.ApplicationContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

/**
Created by Shitab Mir on 21/5/24.
shitabmir@gmail.com
 **/
@Module
@InstallIn(SingletonComponent::class)
class SharedPreferenceDIModule {

 @Provides
 @Singleton
 fun provideSharedPreferences(): SharedPreferences? {
  return ApplicationContextProvider.getContext()
   ?.getSharedPreferences(SharedPreferenceConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE)
 }

 @Provides
 @Singleton
 fun provideSharedPreferenceManager(sharedPreferences: SharedPreferences?): SharedPreferenceManager {
  return SharedPreferenceManager(sharedPreferences)
 }

}