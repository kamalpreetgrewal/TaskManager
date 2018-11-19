package com.example.kamalpreetgrewal.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class TaskActivity extends FragmentActivity {

    public static final String EXTRA_TASK_ID = "com.example.kamalpreetgrewal.taskmanager.task_id";

    @Override
    protected Fragment createFragment() {
        return new TaskFragment();
    }

    public static Intent newIntent(Context context, UUID taskId) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }
}
