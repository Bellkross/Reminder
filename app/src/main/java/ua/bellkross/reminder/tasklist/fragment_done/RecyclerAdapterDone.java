package ua.bellkross.reminder.tasklist.fragment_done;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Locale;

import ua.bellkross.reminder.R;
import ua.bellkross.reminder.database.DBHelper;
import ua.bellkross.reminder.tasklist.TaskAdapter;
import ua.bellkross.reminder.tasklist.model.ArrayListDTasks;
import ua.bellkross.reminder.tasklist.model.Task;

import static ua.bellkross.reminder.tasklist.TaskListActivity.DONE_STATE;

public class RecyclerAdapterDone extends RecyclerView.Adapter<MyViewHolder> implements TaskAdapter {
    public static RecyclerAdapterDone instance;
    private LayoutInflater layoutInflater;
    private ArrayList<Task> arrayList;
    private View.OnClickListener onClickListener;

    private RecyclerAdapterDone(Context context, View.OnClickListener onClickListener) {
        this.instance = this;
        this.onClickListener = onClickListener;
        layoutInflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
        sort();
    }

    private RecyclerAdapterDone(Context context) {
        this.instance = this;
        layoutInflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
        sort();
    }

    public static RecyclerAdapterDone getInstance(Context context, View.OnClickListener onClickListener) {
        return instance == null ? new RecyclerAdapterDone(context, onClickListener) : instance;
    }

    public static RecyclerAdapterDone getInstance(Context context) {
        return instance == null ? new RecyclerAdapterDone(context) : instance;
    }

    public static RecyclerAdapterDone getInstance() {
        return instance;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rv_item_done, parent, false);
        view.setOnClickListener(onClickListener);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task inputTask = ArrayListDTasks.getInstance().get(position);
        holder.getTvTask().setText(inputTask.getTask());
        holder.getTvDeadline().setText(inputTask.getDeadline());
    }

    @Override
    public int getItemCount() {
        return ArrayListDTasks.getInstance().size();
    }

    @Override
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ArrayListDTasks.getInstance().clear();
        if (charText.length() == 0) {
            ArrayListDTasks.getInstance().addAll(arrayList);
        } else {
            for (Task wp : arrayList) {
                if (wp.getTask().toLowerCase(Locale.getDefault()).contains(charText)) {
                    ArrayListDTasks.getInstance().add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void add(Task inputTask) {
        ArrayListDTasks.getInstance().add(inputTask);
        arrayList.add(inputTask);
        DBHelper.getInstance().addInDB(inputTask);
        sort();
    }

    @Override
    public void update(Task inputTask, int listPos, int dbPos) {

    }

    @Override
    public void sort() {
        arrayList = DBHelper.getInstance().sortTasks(DONE_STATE);
        ArrayListDTasks.getInstance().clear();
        ArrayListDTasks.getInstance().addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public void clearAll() {
        DBHelper.getInstance().clearAll(DONE_STATE);
        ArrayListDTasks.getInstance().clear();
        arrayList = ArrayListDTasks.getInstance();
        notifyDataSetChanged();
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
