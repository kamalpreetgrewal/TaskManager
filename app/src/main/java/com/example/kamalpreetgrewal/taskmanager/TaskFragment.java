package com.example.kamalpreetgrewal.taskmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * This fragment gets the information of a task like its name, description and completion
 * status and then this information is used to populate the list item in recyclerview with the
 * information filled in here.
 */
public class TaskFragment extends Fragment {
    private Task mTask;
    private EditText mTaskName;
    private EditText mTaskDescription;
    private CheckBox mCompletedCheckbox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the ID generated and assign it to the new task being created.
        UUID taskId = (UUID) getActivity().getIntent().getSerializableExtra(TaskActivity.EXTRA_TASK_ID);
        mTask = TaskListManager.getInstance(getActivity()).getTask(taskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        mTaskName = (EditText) view.findViewById(R.id.task_name);
        mTaskName.setText(mTask.getTaskName());
        mTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // Once the changes are done, assign the input text to the new task name attribute.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setTaskName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTaskDescription = (EditText) view.findViewById(R.id.task_description);
        mTaskDescription.setText(mTask.getTaskDescription());
        mTaskDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // Once the changes are done, assign the information filled to description attribute.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setTaskDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCompletedCheckbox = (CheckBox) view.findViewById(R.id.task_completion_checkbox);
        mCompletedCheckbox.setChecked(mTask.isTaskCompleted());
        mCompletedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTask.setTaskCompleted(isChecked);
            }
        });

        return view;
    }
}