package com.mir.cachemodule.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created by Shitab Mir on 21/5/24.
shitabmir@gmail.com
 **/
@Entity(tableName = "user_table")
data class UserTable(
 @PrimaryKey(autoGenerate = true) val id: Int,
 @ColumnInfo(name = "first_name") val firstName: String,
 @ColumnInfo(name = "last_name") val lastName: String,
 val age: Int
)