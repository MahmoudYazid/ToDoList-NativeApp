package com.example.todo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.adaptors.TasksAdpaptor
import com.example.todo.repository.DbTasksEntity
import com.example.todo.repository.TasksDb
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        val TasksRecycler: RecyclerView = findViewById(R.id.TasksRecycler)
        val SendBtmRef: Button = findViewById(R.id.ButtomSend)
        val SendTextRef: EditText = findViewById(R.id.TextEditor)
        val FloatingActionButtonRef: ImageView = findViewById(R.id.imageViewIcon)
        val instance =TasksDb.getInstance(this);

        // Initialize the adapter with an empty list





        val adaptorData = TasksAdpaptor(mutableListOf(),instance)












        // Fetch data from the database using coroutines
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val dataList: List<DbTasksEntity>? = TasksDb.getInstance(context = this@MainActivity).userDao().getAll()
                // Update the UI on the main thread with the fetched data
                withContext(Dispatchers.Main) {
                    adaptorData.SetData(dataList)
                    adaptorData.notifyDataSetChanged()
                }
            }
        }



        TasksRecycler.adapter = adaptorData



        FloatingActionButtonRef.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/mahmoudyazid/"))


            startActivity(browserIntent)


        }








        SendBtmRef.setOnClickListener {
            lifecycleScope.launch {
                // Use withContext to switch to the IO dispatcher for database operation
                withContext(Dispatchers.IO) {
                    val newTask = DbTasksEntity(
                        id = null,
                        taks = SendTextRef.text.toString(),
                        day = LocalDate.now().toString() ,
                        finished = "no"
                    )

                    // Insert the new task into the database
                    TasksDb.getInstance(context = this@MainActivity).userDao().insertAll(newTask)

                    // Fetch updated data from the database
                    val updatedDataList: List<DbTasksEntity>? =
                        TasksDb.getInstance(context = this@MainActivity).userDao().getAll()

                    // Update the adapter with the new data
                    withContext(Dispatchers.Main) {
                        adaptorData.SetData(updatedDataList)
                        adaptorData.notifyDataSetChanged()

                    }
                }
            }
        }














    }
}
