package ua.bellkross.reminder.tasklist.fragment_not_done;

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
import ua.bellkross.reminder.tasklist.fragment_not_done.model.ArrayListNDTasks;

public class NotDoneFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final NotDoneFragment instance = new NotDoneFragment();
    private RecyclerView recyclerView;
    private SearchView searchView;

    public NotDoneFragment() {
    }

    public static NotDoneFragment getInstance() {
        return instance;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_all:
                RecyclerAdapterND.getInstance().clearAll();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_not_done, container, false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recycler_not_done);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(RecyclerAdapterND.getInstance(getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int recyclerViewPosition = recyclerView.getChildAdapterPosition(view);
                RecyclerAdapterND.getInstance().remove(ArrayListNDTasks.getInstance().
                        get(recyclerViewPosition).getPositionInDatabase());
            }
        }));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_not_done, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s;
        RecyclerAdapterND.getInstance().filter(text);
        return false;
    }

}
