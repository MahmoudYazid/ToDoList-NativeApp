package com.example.todo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.ViewModel.ViewModelClass
import com.example.todo.adaptors.IOnTaskClickListner
import com.example.todo.adaptors.TasksAdpaptor
import com.example.todo.repository.DbTasksEntity
import com.example.todo.repository.TasksDb
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    val viewmodelClassInst:ViewModelClass by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        val TasksRecycler: RecyclerView = findViewById(R.id.TasksRecycler)
        val SendBtmRef: Button = findViewById(R.id.ButtomSend)
        val SendTextRef: EditText = findViewById(R.id.TextEditor)
        val FloatingActionButtonRef: ImageView = findViewById(R.id.imageViewIcon)
        val instance =TasksDb.getInstance(this);

        // Initialize the adapter with an empty list




        viewmodelClassInst.GetData(this@MainActivity)

        val adaptorData = TasksAdpaptor(mutableListOf(),instance)
        viewmodelClassInst.LiveDataRef.observe(this@MainActivity) {
            adaptorData.SetData(it)
            adaptorData.notifyDataSetChanged()
        }


        // Fetch data from the database using coroutines



        TasksRecycler.adapter = adaptorData



        FloatingActionButtonRef.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/mahmoudyazid/"))


            startActivity(browserIntent)


        }





        adaptorData.OnClickLisnterRef= object :IOnTaskClickListner{
            override fun onTaskClickDelete(Task: DbTasksEntity, positin: Int) {
                lifecycleScope.launch{
                    withContext(Dispatchers.IO){
                        TasksDb.getInstance(this@MainActivity).userDao().delete(Task);

                    }
                    withContext(Dispatchers.Main){
                        adaptorData.notifyItemRemoved(positin)
                        adaptorData.DataList.remove(Task)
                    }
                }

            }

            override fun onTaskClickUpdateTask(TaskID: Int, NewTaskText: String) {
                lifecycleScope.launch{
                    withContext(Dispatchers.Main){
                        viewmodelClassInst.ModifyTask(this@MainActivity,TaskID,NewTaskText)

                        //show toast

                    }
                }
            }

        }

        SendBtmRef.setOnClickListener {

                // Use withContext to switch to the IO dispatcher for database operation
                    lifecycleScope.launch {
                        withContext(Dispatchers.Main){
                            val newTask = DbTasksEntity(
                                id = null,
                                taks = SendTextRef.text.toString(),
                                day = LocalDate.now().toString() ,
                                finished = "no"
                            )
                            viewmodelClassInst.AddTask(newTask,this@MainActivity)


                        }

                }
            }
        }














    }

