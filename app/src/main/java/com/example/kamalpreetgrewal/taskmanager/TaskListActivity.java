package com.example.kamalpreetgrewal.taskmanager;

import android.support.v4.app.Fragment;

/**
 * This activity hosts the TaskListFragment.
 */
public class TaskListActivity extends FragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }
}
