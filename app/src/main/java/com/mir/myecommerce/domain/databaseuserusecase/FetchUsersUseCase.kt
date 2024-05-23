package com.mir.myecommerce.domain.databaseuserusecase

import com.mir.cachemodule.database.UserTable
import com.mir.myecommerce.data.repository.databaserepository.DatabaseRepository
import javax.inject.Inject

/**
Created by Shitab Mir on 23/5/24.
shitabmir@gmail.com
 **/
class FetchUsersUseCase @Inject constructor(private val repository: DatabaseRepository) {
 suspend operator fun invoke(): List<UserTable> = run {
  return repository.getAllUsers()
 }

}