package ua.bellkross.reminder.tasklist.fragment_done;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import ua.bellkross.reminder.R;
import ua.bellkross.reminder.edit.EditActivity;
import ua.bellkross.reminder.tasklist.model.ArrayListDTasks;
import ua.bellkross.reminder.tasklist.model.ArrayListNDTasks;
import ua.bellkross.reminder.tasklist.model.Task;

import static ua.bellkross.reminder.tasklist.TaskListActivity.ADD_ELEMENT_ACTION_TAG;
import static ua.bellkross.reminder.tasklist.TaskListActivity.POSITION_IN_LIST_TAG;
import static ua.bellkross.reminder.tasklist.TaskListActivity.STATE_ITEM_TAG;

public class DoneFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final DoneFragment instance = new DoneFragment();
    private RecyclerView recyclerView;
    private SearchView searchView;

    public DoneFragment() {
        // Required empty public constructor
    }

    public static DoneFragment getInstance(){
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_done, container, false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recycler_done);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerAdapterDone.getInstance().setOnClickListener(view1 -> {
            int recyclerViewPosition = recyclerView.getChildAdapterPosition(view1);
            Intent intent = new Intent(getContext().getApplicationContext(), EditActivity.class);
            Task tmpTask = ArrayListDTasks.getInstance().get(recyclerViewPosition);
            intent.putExtra(ADD_ELEMENT_ACTION_TAG, false);
            intent.putExtra(POSITION_IN_LIST_TAG, tmpTask.getPositionInList());
            intent.putExtra(STATE_ITEM_TAG, tmpTask.getDone());
            startActivity(intent);
        });
        RecyclerAdapterDone.getInstance().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int recyclerViewPosition = recyclerView.getChildAdapterPosition(v);
                RecyclerAdapterDone.getInstance().remove(ArrayListDTasks.getInstance().
                        get(recyclerViewPosition).getPositionInDatabase());
                return true;
            }
        });
        recyclerView.setAdapter(RecyclerAdapterDone.getInstance());
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_all_done:
                RecyclerAdapterDone.getInstance().clearAll();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_done, menu);

        searchView = (SearchView) menu.findItem(R.id.search_done).getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s;
        RecyclerAdapterDone.getInstance().filter(text);
        return false;
    }

}
