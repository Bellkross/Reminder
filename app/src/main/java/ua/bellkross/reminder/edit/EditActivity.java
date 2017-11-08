package ua.bellkross.reminder.edit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ua.bellkross.reminder.R;
import ua.bellkross.reminder.tasklist.TaskListActivity;

public class EditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        add = getIntent().getBooleanExtra(TaskListActivity.ADD_ELEMENT_ACTION_TAG, false);
        initToolbar();
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_activity, menu);
        if (add){
            menu.findItem(R.id.eam_delete).setVisible(false);
            menu.findItem(R.id.eam_share).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }
}
