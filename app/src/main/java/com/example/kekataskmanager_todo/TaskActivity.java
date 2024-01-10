package com.example.kekataskmanager_todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kekataskmanager_todo.Adapters.TaskAdapter;
import com.example.kekataskmanager_todo.CreateTask;
import com.example.kekataskmanager_todo.Models.Task;
import com.example.kekataskmanager_todo.Models.TaskDatabaseHelper;
import com.example.kekataskmanager_todo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter newTaskAdapter,completedTaskAdapter,activeTaskAdapater,totalTaskAdapter;

    private Button newTask, completedTask,activeTask,totalTask;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<Task> newTaskList,activeTaskList,completedTaskList,totalTaskList;

    private TaskDatabaseHelper taskDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        sharedPreferences  = getSharedPreferences("TODO",MODE_PRIVATE);
        editor = sharedPreferences.edit()

        newTask = findViewById(R.id.newTasks);
        activeTask = findViewById(R.id.activeTasks);
        completedTask = findViewById(R.id.completedTasks);
        totalTask = findViewById(R.id.totalTasks);


        taskDatabaseHelper = new TaskDatabaseHelper(this);

        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayNewTasks();

        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayNewTasks();

            }
        });

        activeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayActiveTasks();
            }
        });

        completedTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCompletedTasks();
            }
        });

        totalTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTotalTasks();
            }
        });



    }


    public void displayNewTasks(){
        newTaskList = new ArrayList<>();
//            SQL for new Tasks
        newTaskList = taskDatabaseHelper.getTasksByStatus(0);
        newTaskAdapter = new TaskAdapter(this, newTaskList,this);
        recyclerView.setAdapter(newTaskAdapter);

        editor.putString("newTasks",newTaskList.size());

    }

    public void displayCompletedTasks(){
        completedTaskList = new ArrayList<>();
        completedTaskList = taskDatabaseHelper.getTasksByStatus(2);
//        SQL for Completed
            completedTaskAdapter = new TaskAdapter(this, completedTaskList,this);
            recyclerView.setAdapter(completedTaskAdapter);

        editor.putString("CompletedTasks",completedTaskList.size());
    }

    public void displayActiveTasks(){
        activeTaskList = new ArrayList<>();
        activeTaskList = taskDatabaseHelper.getTasksByStatus(1);
        activeTaskAdapater = new TaskAdapter(this,activeTaskList,this);
        recyclerView.setAdapter(activeTaskAdapater);
        editor.putString("ActiveTasks",activeTaskList.size());
    }

    public void displayTotalTasks(){
        totalTaskList =  new ArrayList<>();
        totalTaskList = taskDatabaseHelper.getTasksByStatus(0);
        totalTaskList.addAll(taskDatabaseHelper.getTasksByStatus(1));
        totalTaskList.addAll(taskDatabaseHelper.getTasksByStatus(2));
        totalTaskAdapter = new TaskAdapter(this,totalTaskList,this);
        recyclerView.setAdapter(totalTaskAdapter);
        editor.putString("TotalTasks",totalTaskList.size());
    }

}
