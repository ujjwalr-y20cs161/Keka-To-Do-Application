package com.example.kekataskmanager_todo.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Task {

    // Constants for task status
    public  static final int STATUS_CREATED = 0;
    public static final int STATUS_IN_PROGRESS = 1;
    public static final int STATUS_COMPLETED = 2;

    // Constants for priority levels
    public static final int LOW = 0;
    public static final int MEDIUM = 1;
    public static final int HIGH = 2;

    private long taskID;
    private String title;
    private String description;
    private String date;
    private int priorityLevel;
    private int status;

    // Constructors
    public Task(String title, String description, String date, int priorityLevel) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.priorityLevel = priorityLevel;
        this.status = STATUS_CREATED;
    }

    public Task(int taskID, String title, String description, String date, int priorityLevel, int status) {
        this.taskID = taskID;
        this.title = title;
        this.description = description;
        this.date = date;
        this.priorityLevel = priorityLevel;
        this.status = status;
    }

    // Getter and setter methods

    public long getTaskID() {
        return taskID;
    }

    public void setTaskID(long taskID) {
        this.taskID = taskID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Method to save the task to the database
    public void saveTask(Context context) {
        TaskDatabaseHelper dbHelper = new TaskDatabaseHelper(context);

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(TaskDatabaseHelper.COLUMN_TITLE, title);
            values.put(TaskDatabaseHelper.COLUMN_DESCRIPTION, description);
            values.put(TaskDatabaseHelper.COLUMN_DATE, date);
            values.put(TaskDatabaseHelper.COLUMN_PRIORITY_LEVEL, priorityLevel);
            values.put(TaskDatabaseHelper.COLUMN_STATUS, status);

            // Insert the new row
            db.insert(TaskDatabaseHelper.TABLE_TASKS, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }
}

