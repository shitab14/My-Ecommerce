package com.mir.myecommerce.di

import android.content.Context
import com.mir.cachemodule.database.AppDatabase
import com.mir.cachemodule.database.UserTableDao
import com.mir.myecommerce.data.repository.databaserepository.DatabaseRepository
import com.mir.myecommerce.data.repository.databaserepository.DatabaseRepositoryImp
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
class DatabaseDIModule {
    @Provides
    @Singleton
    fun provideDatabaseRepository(userDao: UserTableDao): DatabaseRepository {
        return DatabaseRepositoryImp(userDao = userDao)
    }
    @Provides
    @Singleton
    fun provideUserTableDao(
        appDatabase: AppDatabase
    ): UserTableDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context.applicationContext)
    }

}