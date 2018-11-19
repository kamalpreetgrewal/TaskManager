package com.example.kamalpreetgrewal.taskmanager;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskListManager {
    private static TaskListManager sTaskList;
    private static List<Task> mTasks;

    private TaskListManager(Context context) {
        mTasks = new ArrayList<>();
    }

    public static TaskListManager getInstance(Context context) {
        if (sTaskList == null) {
            sTaskList = new TaskListManager(context);
        }
        return sTaskList;
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public Task getTask(UUID id) {
        for (Task task : mTasks) {
            if (task.getTaskId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public void addTask(Task task) {
        mTasks.add(task);
    }
}