package ua.bellkross.reminder.tasklist.fragment_not_done;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ua.bellkross.reminder.R;

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView tvTask;
    private TextView tvDeadline;

    public MyViewHolder(View itemView) {
        super(itemView);

        tvTask = itemView.findViewById(R.id.text_not_done1);
        tvDeadline = itemView.findViewById(R.id.text_not_done2);
        //task.setPaintFlags(task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public TextView getTvTask() {
        return tvTask;
    }

    public TextView getTvDeadline() {
        return tvDeadline;
    }
}