package com.example.todo.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbTasksEntity::class], version = 2)
abstract class TasksDb : RoomDatabase() {

    abstract fun userDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: TasksDb? = null

        fun getInstance(context: Context): TasksDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDb::class.java,
                    "tasks.db"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }


    }
}
