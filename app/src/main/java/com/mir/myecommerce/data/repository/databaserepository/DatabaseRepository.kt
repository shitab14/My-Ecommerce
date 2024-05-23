package com.mir.myecommerce.data.repository.databaserepository

import com.mir.cachemodule.database.UserTable

/**
Created by Shitab Mir on 21/5/24.
shitabmir@gmail.com
 **/
interface DatabaseRepository {
 suspend fun getAllUsers(): List<UserTable>
 suspend fun getUserByUserId(id: Int): UserTable
 suspend fun insertUser(user: UserTable)
 suspend fun updateUser(user: UserTable)
 suspend fun deleteUser(user: UserTable)
 suspend fun clearAllUsers()

}