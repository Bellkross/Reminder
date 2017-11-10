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
import ua.bellkross.reminder.tasklist.fragment_not_done.RecyclerAdapterND;

public class EditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean add;
    private EditText etTask;
    private EditText etDate;
    private EditText etTime;

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
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_edit_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (add) {
            getSupportActionBar().setTitle("New Task");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishAndRemoveTask();
        } else if (item.getItemId() == R.id.eam_accept) {
            if(!etDate.getText().toString().equals("")&&
                    !etTask.getText().toString().equals("")&&
                    !etTime.getText().toString().equals("")) {
                RecyclerAdapterND.getInstance().add(etTask.getText().toString(), etDate.getText() + " " + etTime.getText());
                finishAndRemoveTask();
            }else {
                Toast.makeText(getApplicationContext(),"Input information about task", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
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
