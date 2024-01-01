package com.example.todo.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todo.repository.Dao
import com.example.todo.repository.TasksDb


class ViewModelClass :ViewModel() {



    fun getItemCountFromViewModel(): Int {

        return 0;

    }


}