package com.example.kamalpreetgrewal.taskmanager;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * This fragment is the soul of this application.
 */
public class TaskListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mTaskAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static TextView mDefaultTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tell here that menu options are to be considered for performing actions.
        setHasOptionsMenu(true);

        /**
         * Since there are no tasks in the beginning i.e. for the first time the application is
         * run, the user is presented with a suitable message telling what should be his/her first
         * action to proceed to use the application to create tasks.
         */
        mDefaultTextView = (TextView) getActivity().findViewById(R.id.default_textview);
        mDefaultTextView.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        // Define recyclerview, its adapter and holder here.
        mRecyclerView = view.findViewById(R.id.task_list_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        updateUI();
        return view;
    }

    /**
     * This function brings up the swipe gesture from the right most side of the screen to left
     * for each of the tasks. When the swipe is done midway/partially, nothing happens. If it is
     * done till the left side, the task is deleted from the list and the recyclerview is notified
     * of the change and updates the view accordingly.
     */
    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            Drawable background;
            Drawable deleteSign;
            int deleteSignMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                deleteSign = ContextCompat.getDrawable(getActivity(), R.drawable.ic_task_delete);
                deleteSign.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                initiated = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                TaskAdapter testAdapter = (TaskAdapter) recyclerView.getAdapter();
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                TaskAdapter adapter = (TaskAdapter) mRecyclerView.getAdapter();
                adapter.remove(swipedPosition);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                if (viewHolder.getAdapterPosition() == -1) {
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(),
                        itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = deleteSign.getIntrinsicWidth();
                int intrinsicHeight = deleteSign.getIntrinsicWidth();

                int deleteSignLeft = itemView.getRight() - deleteSignMargin - intrinsicWidth;
                int deleteSignRight = itemView.getRight() - deleteSignMargin;
                int deleteSignTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int deleteSignBottom = deleteSignTop + intrinsicHeight;
                deleteSign.setBounds(deleteSignLeft, deleteSignTop, deleteSignRight, deleteSignBottom);

                deleteSign.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * This method updates the recyclerview with the 'tasks' list by passing this list to
     * the TaskAdapter.
     */
    private void updateUI() {
        TaskListManager listManager = TaskListManager.getInstance(getActivity());
        List<Task> tasks = listManager.getTasks();

        /**
         * If the adapter is null, create new one. If it already exists, notify the adapter of
         * any changes that are made in the data.
         */
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.notifyDataSetChanged();
        }

        // This call brings up delete option by swiping in the list from right to left.
        setUpItemTouchHelper();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task:
                /**
                 * When + is clicked in action bar, new task is created and added to list of tasks
                 * and new fragment opens up where the user has to fill in task information. The
                 * default text message is set to invisible.
                 */
                Task task = new Task();
                TaskListManager.getInstance(getActivity()).addTask(task);
                Intent intent = TaskActivity.newIntent(getActivity(), task.getTaskId());
                startActivity(intent);
                mDefaultTextView.setVisibility(View.INVISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This class creates a container for an item in the task list. How every task will appear
     * in the list is defined by the values fetched and assigned here.
     */
    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Task mTask;
        private TextView mTaskTitle;
        private TextView mTaskDescription;
        private CheckBox mTaskCompleted;

        public TaskHolder(View view) {
            super(view);
            mTaskTitle = (TextView) view.findViewById(R.id.task_title);
            mTaskDescription = (TextView) view.findViewById(R.id.task_desc);
            mTaskCompleted = (CheckBox) view.findViewById(R.id.task_completed);
        }

        public void bind(Task task) {
            mTask = task;
            mTaskTitle.setText(mTask.getTaskName());
            mTaskDescription.setText(mTask.getTaskDescription());
            mTaskCompleted.setChecked(mTask.isTaskCompleted());

            if (mTaskCompleted.isChecked()) {
                mTask.setTaskCompleted(true);
            } else {
                mTask.setTaskCompleted(false);
            }
        }

        @Override
        public void onClick(View v) {
        }
    }

    /**
     * This class gets the data from the controller class TaskListManager and attaches it to
     * the recyclerview. The TaskHolder for each class is created here. If the user swipes from
     * right to left, it is deleted via the call in remove function of this class.
     */
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_item,
                    parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        public void remove(int position) {
            mTasks.remove(position);
            notifyItemRemoved(position);
        }
    }
}