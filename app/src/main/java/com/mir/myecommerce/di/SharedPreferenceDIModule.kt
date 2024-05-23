package com.mir.myecommerce.di

import android.content.Context
import android.content.SharedPreferences
import com.mir.cachemodule.sharedpreference.SharedPreferenceConstants
import com.mir.cachemodule.sharedpreference.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
 fun provideSharedPreferences(
  @ApplicationContext context: Context,
  ): SharedPreferences? {
  return context.getSharedPreferences(SharedPreferenceConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE)
 }

 @Provides
 @Singleton
 fun provideSharedPreferenceManager(sharedPreferences: SharedPreferences?): SharedPreferenceManager {
  return SharedPreferenceManager(sharedPreferences)
 }

}