package com.mir.cachemodule.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
Created by Shitab Mir on 21/5/24.
shitabmir@gmail.com
 **/
@Dao
interface UserTableDao {

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertUser(user: UserTable)

 @Update
 suspend fun updateUser(user: UserTable)

 @Delete
 suspend fun deleteUser(user: UserTable)

 @Query("SELECT * FROM user_table")
 suspend fun getAllUsers(): List<UserTable>

 @Query("SELECT * FROM user_table WHERE id = :userId")
 suspend fun getUserById(userId: Int): UserTable

 @Query("DELETE FROM user_table")
 suspend fun clearAllUsers()

}