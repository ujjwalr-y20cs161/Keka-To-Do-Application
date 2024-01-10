package com.example.kekataskmanager_todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DashBoard extends AppCompatActivity {

    CardView newT,activeT,completeT,totalT,addTask;

    TextView newTasks,activeTasks,completeTasks,totalTasks,userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        SharedPreferences sharedPreferences = getSharedPreferences("TODO",MODE_PRIVATE);

        userName = findViewById(R.id.welcomeText);
        userName.setText("Welcomee "+sharedPreferences.getString("userName","User"));


        newT = findViewById(R.id.New);
        activeT = findViewById(R.id.Active);
        completeT = findViewById(R.id.Complete);
        totalT = findViewById(R.id.Total);
        addTask = findViewById(R.id.taskAdd);

        newTasks = findViewById(R.id.newTasks);
        activeTasks = findViewById(R.id.activeTasks);
        completeTasks = findViewById(R.id.completedTasks);
        totalTasks = findViewById(R.id.totalTasks);

        newTasks.setText("0");
        completeTasks.setText("0");
        activeTasks.setText("0");
        totalTasks.setText("0");

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CreateTask.class));

            }
        });

        newT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextt();
            }
        });

        activeT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextt();
            }
        });

        totalT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextt();
            }
        });

        completeT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextt();
            }
        });

    }

    public void nextt(){
        startActivity(new Intent(getApplicationContext(), TaskActivity.class));
    }
}