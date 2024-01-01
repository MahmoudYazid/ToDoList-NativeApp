package com.example.todo.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao

interface  Dao {
    @Insert
    fun insertAll(task: DbTasksEntity)

    @Delete
    fun delete(task: DbTasksEntity)

    @Query("SELECT * FROM taskstable")
    fun getAll(): List<DbTasksEntity>?

}