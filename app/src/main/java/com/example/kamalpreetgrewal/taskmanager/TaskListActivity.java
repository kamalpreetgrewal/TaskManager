package com.example.kamalpreetgrewal.taskmanager;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TaskListActivity extends FragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }
}
