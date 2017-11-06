package ua.bellkross.reminder.tasklist.fragment_done;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import ua.bellkross.reminder.R;

public class DoneFragment extends Fragment {

    private static final DoneFragment instance = new DoneFragment();

    public DoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_done, container, false);
        return view;
    }
    
    public static DoneFragment getInstance(){
        return instance;
    }
}
