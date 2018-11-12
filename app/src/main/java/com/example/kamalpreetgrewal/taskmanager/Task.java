package com.example.kamalpreetgrewal.taskmanager;

import java.util.UUID;

public class Task {
    private String mTaskName;
    private String mTaskDescription;
    private boolean isTaskCompleted;

    private UUID mTaskId;

    public Task() {
        mTaskName = "";
        mTaskDescription = "";
        isTaskCompleted = false;
        mTaskId = UUID.randomUUID();
    }

    public String getTaskName() {
        return mTaskName;
    }

    public void setTaskName(String taskName) {
        this.mTaskName = taskName;
    }

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.mTaskDescription = taskDescription;
    }

    public boolean isTaskCompleted() {
        return isTaskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        isTaskCompleted = taskCompleted;
    }

    public UUID getTaskId() {
        return mTaskId;
    }

    public void setTaskId(UUID mTaskId) {
        this.mTaskId = mTaskId;
    }
}