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

public class TaskFragment extends Fragment {
    private Task mTask;
    private EditText mTaskName;
    private EditText mTaskDescription;
    private CheckBox mCompletedCheckbox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
