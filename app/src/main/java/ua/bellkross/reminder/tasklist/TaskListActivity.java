package ua.bellkross.reminder.tasklist;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import ua.bellkross.reminder.R;
import ua.bellkross.reminder.database.DBHelper;
import ua.bellkross.reminder.tasklist.fragment_done.DoneFragment;
import ua.bellkross.reminder.tasklist.fragment_not_done.NotDoneFragment;
import ua.bellkross.reminder.tasklist.fragment_not_done.RecyclerAdapterND;
import ua.bellkross.reminder.tasklist.model.ArrayListNDTasks;

public class TaskListActivity extends AppCompatActivity {

    public static final String LOG_TAG = "debug";
    private Toolbar toolbar;
    private Spinner spinner;
    private FloatingActionButton fab;
    private EditText etInputTask;
    private ConstraintLayout notDoneItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        DBHelper.getInstance(this.getApplicationContext());
        initActionBar();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerAdapterND.getInstance().add("Task # " + ArrayListNDTasks.getInstance().size());
            }
        });

        etInputTask = findViewById(R.id.etInputTask);
        etInputTask.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                String task = etInputTask.getText().toString();
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)
                        && !task.equalsIgnoreCase("")) {
                    RecyclerAdapterND.getInstance().add(task);
                    etInputTask.setText("");
                    return true;
                }
                return false;
            }
        });
        notDoneItems = findViewById(R.id.notDoneItems);
    }

    @SuppressLint("RestrictedApi")
    private void initActionBar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setCollapsible(false);
        spinner = findViewById(R.id.spinner);

        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(false, false);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        spinner.setAdapter(new SpinnerAdapter(
                toolbar.getContext(),
                new String[]{
                        "My tasks",
                        "Finished tasks"
                }));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, NotDoneFragment.getInstance()).commit();
                        notDoneItems.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, DoneFragment.getInstance()).commit();
                        notDoneItems.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }
}