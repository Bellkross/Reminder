package ua.bellkross.reminder.edit;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ua.bellkross.reminder.R;
import ua.bellkross.reminder.tasklist.TaskListActivity;
import ua.bellkross.reminder.tasklist.fragment_done.RecyclerAdapterDone;
import ua.bellkross.reminder.tasklist.fragment_not_done.RecyclerAdapterND;
import ua.bellkross.reminder.tasklist.model.ArrayListDTasks;
import ua.bellkross.reminder.tasklist.model.ArrayListNDTasks;
import ua.bellkross.reminder.tasklist.model.Task;

import static ua.bellkross.reminder.tasklist.TaskListActivity.DONE_STATE;
import static ua.bellkross.reminder.tasklist.TaskListActivity.NOT_DONE_STATE;
import static ua.bellkross.reminder.tasklist.TaskListActivity.POSITION_IN_LIST_TAG;
import static ua.bellkross.reminder.tasklist.TaskListActivity.STATE_ITEM_TAG;

public class EditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean add;
    private EditText etTask;
    private EditText etDate;
    private EditText etTime;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        add = getIntent().getBooleanExtra(TaskListActivity.ADD_ELEMENT_ACTION_TAG, false);
        initToolbar();
        etTask = findViewById(R.id.etTask);
        etDate = findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateDialog = new DatePicker();
                dateDialog.show(getFragmentManager(), "datePicker");
            }
        });
        etTime = findViewById(R.id.etTime);
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new MyTimePicker();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
        if (!add) {
            int done = getIntent().getIntExtra(STATE_ITEM_TAG, NOT_DONE_STATE);
            int positionInList = getIntent().getIntExtra(POSITION_IN_LIST_TAG, -1);
            if (done == DONE_STATE) {
                task = ArrayListDTasks.getInstance().get(positionInList);
            } else {
                task = ArrayListNDTasks.getInstance().get(positionInList);
            }
            upload();
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_edit_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (add) {
            getSupportActionBar().setTitle("New Task");
        } else {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishAndRemoveTask();
        } else if (item.getItemId() == R.id.eam_accept) {
            if (add) {
                addTask();
            } else {
                changeTask();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void addTask() {
        if (!etDate.getText().toString().equals("") &&
                !etTask.getText().toString().equals("") &&
                !etTime.getText().toString().equals("")) {
            RecyclerAdapterND.getInstance().add(etTask.getText().toString(), etDate.getText() + " " + etTime.getText());
            finishAndRemoveTask();
        } else {
            Toast.makeText(getApplicationContext(), "Input information about task", Toast.LENGTH_SHORT).show();
        }
    }

    private void upload() {
        etTask.setText(task.getTask().toString());
        etDate.setText(task.getDeadline().substring(0, 11));
        etTime.setText(task.getDeadline().substring(12));
    }

    private void changeTask() {
        task = new Task(etTask.getText().toString(), etDate.getText() + " " + etTime.getText(),
                task.getPositionInList(), task.getPositionInDatabase(), task.getDone());
        if (task.getDone() == NOT_DONE_STATE) {
            RecyclerAdapterND.getInstance().update(task, task.getPositionInDatabase());
        } else {
            RecyclerAdapterDone.getInstance().update(task, task.getPositionInDatabase());
        }

        finishAndRemoveTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_activity, menu);
        if (add) {
            menu.findItem(R.id.eam_delete).setVisible(false);
            menu.findItem(R.id.eam_share).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }
}
