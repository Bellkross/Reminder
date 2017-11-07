package ua.bellkross.reminder.tasklist.fragment_not_done;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Locale;

import ua.bellkross.reminder.R;
import ua.bellkross.reminder.database.DBHelper;
import ua.bellkross.reminder.tasklist.model.ArrayListNDTasks;
import ua.bellkross.reminder.tasklist.model.Task;

public class RecyclerAdapterND extends RecyclerView.Adapter<MyViewHolder>{

    public static RecyclerAdapterND instance;
    private LayoutInflater layoutInflater;
    private ArrayList<Task> arrayList;
    private View.OnClickListener onClickListener;

    private RecyclerAdapterND(Context context, View.OnClickListener onClickListener){

        this.onClickListener = onClickListener;
        layoutInflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
        sort();
        this.arrayList.addAll(ArrayListNDTasks.getInstance());
        this.instance = this;

    }

    public static RecyclerAdapterND getInstance(Context context, View.OnClickListener onClickListener) {
        return instance==null ? new RecyclerAdapterND(context, onClickListener) : instance;
    }

    public static RecyclerAdapterND getInstance() {
        return instance;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.rv_item_not_done, parent, false);
        view.setOnClickListener(onClickListener);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task inputTask = ArrayListNDTasks.getInstance().get(position);
        holder.getTvTask().setText(inputTask.getTask());
        holder.getTvDeadline().setText(inputTask.getDeadline());

    }

    @Override
    public int getItemCount() {
        return ArrayListNDTasks.getInstance().size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ArrayListNDTasks.getInstance().clear();
        if (charText.length() == 0) {
            ArrayListNDTasks.getInstance().addAll(arrayList);
        } else {
            for (Task wp : arrayList) {
                if (wp.getTask().toLowerCase(Locale.getDefault()).contains(charText)) {
                    ArrayListNDTasks.getInstance().add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void add(String task) {
        Task inputTask = new Task(task, ArrayListNDTasks.getInstance().size());
        ArrayListNDTasks.getInstance().add(inputTask);
        arrayList.add(inputTask);
        DBHelper.getInstance().addInDB(inputTask);
        sort();
    }

    public void add(String task, String deadline) {
        Task inputTask = new Task(task, deadline, ArrayListNDTasks.getInstance().size());
        ArrayListNDTasks.getInstance().add(inputTask);
        arrayList.add(inputTask);
        DBHelper.getInstance().addInDB(inputTask);
        sort();
    }

    public void update(Task inputTask, int listPos, int dbPos){
        //DBHelper.getInstance().updateDB(""+posDB,inputTask);
        ArrayListNDTasks.getInstance().set(listPos,inputTask);
        //ArrayListNDTasks.getInstance().sort();
        sort();
    }

    public void remove(int dbPos){
        DBHelper.getInstance().removeFromDB(dbPos+"");
        sort();
    }

    public void sort(){
        arrayList = DBHelper.getInstance().sortTasks();
        ArrayListNDTasks.getInstance().clear();
        ArrayListNDTasks.getInstance().addAll(arrayList);
        notifyDataSetChanged();
    }

    public void clearAll(){
        DBHelper.getInstance().clearAll();
        ArrayListNDTasks.getInstance().clear();
        arrayList = ArrayListNDTasks.getInstance();
        notifyDataSetChanged();
    }

}
