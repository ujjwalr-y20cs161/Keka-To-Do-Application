package com.example.kekataskmanager_todo.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kekataskmanager_todo.Models.Task;
import com.example.kekataskmanager_todo.TaskActivity;
import com.example.kekataskmanager_todo.Models.TaskDatabaseHelper;
import com.example.kekataskmanager_todo.R;
import com.example.kekataskmanager_todo.TaskActivity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;

    private TaskDatabaseHelper taskDatabaseHelper;
    private TaskActivity cont;

    public TaskAdapter(Context context, List<Task> taskList, TaskActivity cont) {
        this.context = context;
        this.taskList = taskList;
        this.cont = cont;
        this.taskDatabaseHelper = new TaskDatabaseHelper(context); // Initialize the helper
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskcard, parent, false);
        return new TaskViewHolder(view,this,cont);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bindTask(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateStatus(int position,int status){
        Task t = taskList.get(position);
        if(t!=null) {
            t.setStatus(status);
            taskDatabaseHelper.updateTask(t);
            notifyDataSetChanged();
        }

    }

    public void deleteTask(int position){
        Task task = taskList.get(position);
        taskList.remove(position);
        taskDatabaseHelper.deleteTask(task.getTaskID());
        notifyItemRangeChanged(position, getItemCount());
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    public void removeTask(int position) {
        taskList.remove(position);
        notifyItemRangeChanged(position, getItemCount());
        notifyItemRemoved(position);

    }
    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskTitle, dueDate, taskDescription, priorityLevel, taskStatus;
        private Button completeButton,startButton,delete;
        private TaskAdapter adapter;

        private TaskActivity cont;
        public TaskViewHolder(@NonNull View itemView, TaskAdapter adapter, TaskActivity cont) {
            super(itemView);
            this.adapter = adapter;
            taskTitle = itemView.findViewById(R.id.TaskTitle);
            dueDate = itemView.findViewById(R.id.dueDate);
            taskDescription = itemView.findViewById(R.id.TaskDescription);
            priorityLevel = itemView.findViewById(R.id.priorityLevel);
            taskStatus = itemView.findViewById(R.id.taskStatus);
            completeButton = itemView.findViewById(R.id.complete);
            startButton = itemView.findViewById(R.id.taskStart);
            delete = itemView.findViewById(R.id.deleteButton);
            this.cont = cont;
        }

        public void bindTask(Task task) {
            taskTitle.setText(task.getTitle());
            dueDate.setText(task.getDate());
            taskDescription.setText(task.getDescription());
            priorityLevel.setText(getPriorityLabel(task.getPriorityLevel()));
            taskStatus.setText(getStatusLabel(task.getStatus()));

            if(task.getStatus() == Task.STATUS_COMPLETED) {
                completeButton.setVisibility(View.GONE);
                startButton.setVisibility(View.GONE);
                delete.setVisibility(View.VISIBLE);
            }
            else if(task.getStatus() == Task.STATUS_IN_PROGRESS){
                completeButton.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }else{
                delete.setVisibility(View.GONE);
                completeButton.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    deleteTask(position);
                }
            });
            completeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle complete button click
                    // You can update the task status or perform any other action here
                    // Remove the task from the list and notify the adapter
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        adapter.updateStatus(position, 2);
                        adapter.removeTask(position);
                        startButton.setVisibility(View.VISIBLE);
                        completeButton.setVisibility(View.GONE);
                    }
//                    Toast.makeText(context.getApplicationContext(), "Completed Task",Toast.LENGTH_SHORT).show();
                }
            });

            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    startButton.setVisibility(View.GONE);
                    completeButton.setVisibility(View.VISIBLE);

                    adapter.updateStatus(pos,1);
                }
            });


        }

        private void handleCompleteButtonClick(int position) {

            // ... (other logic if needed)
        }


        private String getPriorityLabel(int priorityLevel) {
            // Implement logic to get priority label based on priority level
            // For example, you can return "High Priority" if priorityLevel is 2
            switch (priorityLevel){
                case 0 : return "Low";
                case 1: return "Medium";
                case 2: return "High";// Placeholder, replace with your implementation
                default: return "Priority Level";
            }
        }

        private String getStatusLabel(int status) {
            // Implement logic to get status label based on status
            // For example, you can return "In Progress" if status is 1
            switch (status){
                case 0: return "Created";
                case 1: return "In Progress";
                case 2: return "Completed";
                default: return "Status";
            }
// Placeholder, replace with your implementation
        }
    }
}
