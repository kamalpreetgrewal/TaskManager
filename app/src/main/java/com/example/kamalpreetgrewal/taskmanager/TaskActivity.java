package com.example.kamalpreetgrewal.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * This activity hosts the task fragment. The abstract method in the FragmentActivity is defined
 * here to create the new fragment and host it in this activity.
 */
public class TaskActivity extends FragmentActivity {

    public static final String EXTRA_TASK_ID = "com.example.kamalpreetgrewal.taskmanager.task_id";

    @Override
    protected Fragment createFragment() {
        return new TaskFragment();
    }

    /**
     * This method is called from the TaskFragment to pass data to this activity to assign a
     * unique id to the new task being created.
     * @param context
     * @param taskId
     * @return
     */
    public static Intent newIntent(Context context, UUID taskId) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }
}
