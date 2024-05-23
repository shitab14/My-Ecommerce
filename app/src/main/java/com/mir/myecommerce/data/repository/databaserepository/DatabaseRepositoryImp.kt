package com.mir.myecommerce.data.repository.databaserepository

import com.mir.cachemodule.database.UserTable
import com.mir.cachemodule.database.UserTableDao
import javax.inject.Inject

/**
Created by Shitab Mir on 21/5/24.
shitabmir@gmail.com
 **/
class DatabaseRepositoryImp @Inject constructor(private val userDao: UserTableDao): DatabaseRepository {
    override suspend fun getAllUsers(): List<UserTable> = userDao.getAllUsers()
    override suspend fun getUserByUserId(id: Int): UserTable = userDao.getUserById(userId = id)

    override suspend fun insertUser(user: UserTable) {
        userDao.insertUser(user)
    }

    override suspend fun updateUser(user: UserTable) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: UserTable) {
        userDao.deleteUser(user)
    }

    override suspend fun clearAllUsers() {
        userDao.clearAllUsers()
    }

}