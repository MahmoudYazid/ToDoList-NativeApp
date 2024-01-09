package com.example.todo.adaptors

import com.example.todo.repository.DbTasksEntity

interface IOnTaskClickListner {
    fun onTaskClickDelete (Task:DbTasksEntity,positin:Int):Unit
    fun onTaskClickUpdateTask(TaskID:Int,NewTaskText:String):Unit


}