package com.example.todo.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.todo.repository.Dao
import com.example.todo.repository.DbTasksEntity
import com.example.todo.repository.TasksDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate


class ViewModelClass :ViewModel() {
    private val MutableLifeDataList=MutableLiveData<List<DbTasksEntity>>();

    public val LiveDataRef:LiveData<List<DbTasksEntity>> get() = MutableLifeDataList ;



    fun AddTask(NewTask:DbTasksEntity,context: Context):Unit{
        TasksDb.getInstance(context = context).userDao().insertAll(NewTask)
        GetData(context)

    }
    fun GetData (context: Context): Unit {
        MutableLifeDataList.value= TasksDb.getInstance(context).userDao().getAll()

    }
    fun ModifyTask(context: Context,TaskID:Int,NewTaskText:String):Unit{
        TasksDb.getInstance(context).userDao().UpdateTask(TaskID,NewTaskText)
        GetData(context)
        android.widget.Toast.makeText(context,"Task has been Modified ",android.widget.Toast.LENGTH_SHORT)
            .show()

    }


}