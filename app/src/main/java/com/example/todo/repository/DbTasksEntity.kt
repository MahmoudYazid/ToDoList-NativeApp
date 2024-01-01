package com.example.todo.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taskstable")

data class DbTasksEntity (
        @PrimaryKey(autoGenerate = true) val id: Int?,
        val taks: String,
        val day :String,
        val finished:String


)