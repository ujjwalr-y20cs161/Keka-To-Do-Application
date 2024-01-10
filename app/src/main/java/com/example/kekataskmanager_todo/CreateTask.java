package com.example.kekataskmanager_todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kekataskmanager_todo.Models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Button createTaskBtn = findViewById(R.id.createTaskBtn);



        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Find views
                EditText taskTitle = findViewById(R.id.taskTitle);
                EditText taskDescription = findViewById(R.id.taskDescription);
                RadioGroup priorityBox = findViewById(R.id.priorityBox);
                EditText editTextDate = findViewById(R.id.editTextDate);

                // Validate form fields
                if (taskTitle.getText().toString().trim().isEmpty() ||
                        taskDescription.getText().toString().trim().isEmpty() ||
                        priorityBox.getCheckedRadioButtonId() == -1 ||
                        editTextDate.getText().toString().trim().isEmpty()) {

                    // Display error message
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();

                    // Optionally, set focus to the first unfilled field
                    if (taskTitle.getText().toString().trim().isEmpty()) {
                        taskTitle.requestFocus();
                        taskTitle.setError("Title Please");
                    }else if (priorityBox.getCheckedRadioButtonId() == -1) {
                        // Handle radio group case if none is selected

                        Toast.makeText(getApplicationContext(), "Please select a priority", Toast.LENGTH_SHORT).show();
                    } else if (editTextDate.getText().toString().trim().isEmpty()) {
                        editTextDate.requestFocus();
                        editTextDate.setError("Date Please");
                    }
                } else {
                    // Continue with task creation logic
                    // ...
                    RadioButton selectedRadioButton = findViewById(priorityBox.getCheckedRadioButtonId());

                    String selectedValue = selectedRadioButton.getText().toString();

                    int priorityLevel;

                    switch (selectedValue){
                        case "Low" : priorityLevel = 0;
                            break;
                        case "Medium" : priorityLevel = 1;
                            break;
                        case "High" : priorityLevel = 2;
                            break;
                        default: priorityLevel = 0;
                    }

                    Task T = new Task(taskTitle.getText().toString(),taskDescription.getText().toString(),editTextDate.getText().toString(),priorityLevel);


                    T.saveTask(getApplicationContext());

                    Toast.makeText(getApplicationContext(), "Task created successfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), TaskActivity.class));
                    finish();

                }
            }
        });


    }

}