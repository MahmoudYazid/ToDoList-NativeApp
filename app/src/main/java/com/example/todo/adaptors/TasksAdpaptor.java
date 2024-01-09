package com.example.todo.adaptors;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;

import com.example.todo.R;
import com.example.todo.ViewModel.ViewModelClass;
import com.example.todo.repository.DbTasksEntity;
import com.example.todo.repository.TasksDb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

public class TasksAdpaptor extends RecyclerView.Adapter<TasksAdpaptor.ViewHolderController>{

    public List<DbTasksEntity> DataList;
    TasksDb DbObj;
    public IOnTaskClickListner OnClickLisnterRef;
    public TasksAdpaptor(List<DbTasksEntity> DataInput,TasksDb x){

        this.DataList=DataInput;
        this.DbObj= x;
    }
    public void SetData (List<DbTasksEntity> DataInput){
        DataList=DataInput;

    }



    @NonNull
    @Override
    public ViewHolderController onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View view = inflator.inflate(R.layout.activity_to_do_item,parent,false);
        return new ViewHolderController(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderController holder, int position) {
        DbTasksEntity task1 = DataList.get(position);
        TextView textView = holder.itemView.findViewById(R.id.textView);
        TextView textViewDate = holder.itemView.findViewById(R.id.textView2);
        CheckBox CheckBoxView = holder.itemView.findViewById(R.id.checkBox);
        TextView updateTextView3 = holder.itemView.findViewById(R.id.textView3);

        textView.setText(task1.getTaks());
        textViewDate.setText(task1.getDay());


        updateTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickLisnterRef.onTaskClickUpdateTask(task1.getId(),textView.getText().toString());
            }
        });

        CheckBoxView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Handle the checked state (perform delete operation)
                        OnClickLisnterRef.onTaskClickDelete(task1,position);



                }
            }});




    }

    @Override
    public int getItemCount() {



        return  DataList.size();
    }

    class ViewHolderController extends RecyclerView.ViewHolder{

        public ViewHolderController(@NonNull View itemView) {

            super(itemView);

            TextView taskNameTextView = itemView.findViewById(R.id.textView);
            TextView taskDateTextView = itemView.findViewById(R.id.textView2);
            TextView updateTextView3 = itemView.findViewById(R.id.textView3);
            CheckBox CheckBoxView = itemView.findViewById(R.id.checkBox);


        }


    }
}
