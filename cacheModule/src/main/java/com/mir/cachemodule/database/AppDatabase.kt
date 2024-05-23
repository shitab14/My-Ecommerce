package com.mir.cachemodule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
Created by Shitab Mir on 21/5/24.
shitabmir@gmail.com
 **/
@Database(entities = [UserTable::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
 abstract fun userDao(): UserTableDao
 companion object {
  private const val DATABASE_NAME = "my_app_db"

  @Volatile
  private var INSTANCE: AppDatabase? = null

  fun getInstance(context: Context): AppDatabase {
   if (INSTANCE == null) {
    synchronized(AppDatabase::class.java) {
     if (INSTANCE == null) {
      INSTANCE = Room.databaseBuilder(
       context.applicationContext,
       AppDatabase::class.java,
       DATABASE_NAME
      )
       .fallbackToDestructiveMigration()
       .build()
     }
    }
   }
   return INSTANCE!!
  }
 }
}