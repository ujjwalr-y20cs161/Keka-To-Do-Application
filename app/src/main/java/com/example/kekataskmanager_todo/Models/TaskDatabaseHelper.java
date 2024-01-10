package com.example.kekataskmanager_todo.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PRIORITY_LEVEL = "priority_level";
    public static final String COLUMN_STATUS = "status";

    // SQL statement to create the tasks table
    private static final String DATABASE_CREATE = "create table " +
            TABLE_TASKS + "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null, " +
            COLUMN_DESCRIPTION + " text, " +
            COLUMN_DATE + " text, " +
            COLUMN_PRIORITY_LEVEL + " integer, " +
            COLUMN_STATUS + " integer);";

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade strategy if needed, not implemented in this example
    }

    // Insert a new task into the database
    public long insertTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, task.getTitle());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_DATE, task.getDate());
        values.put(COLUMN_PRIORITY_LEVEL, task.getPriorityLevel());
        values.put(COLUMN_STATUS, task.getStatus());

        // Insert the new task into the database
        long newRowId = db.insert(TABLE_TASKS, null, values);

        // Close the database connection
        db.close();

        return newRowId;
    }

    // Retrieve tasks based on their status
    public List<Task> getTasksByStatus(int status) {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_DESCRIPTION,
                COLUMN_DATE,
                COLUMN_PRIORITY_LEVEL,
                COLUMN_STATUS
        };

        // Define the selection criteria
        String selection = COLUMN_STATUS + " = ?";
        String[] selectionArgs = {String.valueOf(status)};

        // Query the database
        Cursor cursor = db.query(
                TABLE_TASKS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Iterate through the cursor and populate the taskList
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int taskId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                int priorityLevel = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY_LEVEL));
                int taskStatus = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS));

                Task task = new Task(taskId, title, description, date, priorityLevel, taskStatus);
                taskList.add(task);
            }
            cursor.close();
        }

        // Close the database connection
        db.close();

        return taskList;
    }

    // Update an existing task in the database
    public int updateTask(Task modifiedTask) {
        if(modifiedTask != null) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, modifiedTask.getTitle());
            values.put(COLUMN_DESCRIPTION, modifiedTask.getDescription());
            values.put(COLUMN_DATE, modifiedTask.getDate());
            values.put(COLUMN_PRIORITY_LEVEL, modifiedTask.getPriorityLevel());
            values.put(COLUMN_STATUS, modifiedTask.getStatus());

            // Define the selection criteria
            String selection = COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(modifiedTask.getTaskID())};

            // Update the task in the database
            int rowsAffected = db.update(TABLE_TASKS, values, selection, selectionArgs);

            // Close the database connection
            db.close();

            return rowsAffected;
        }else{
            return  0;
        }
    }

    // Delete a task from the database
    public int deleteTask(long taskID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the selection criteria
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskID)};

        // Delete the task from the database
        int rowsDeleted = db.delete(TABLE_TASKS, selection, selectionArgs);

        // Close the database connection
        db.close();

        return rowsDeleted;
    }

    // Additional methods for other CRUD operations as needed
}
