package ua.bellkross.reminder.tasklist.fragment_done;


import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ua.bellkross.reminder.R;

public class MyViewHolder extends RecyclerView.ViewHolder{

    TextView tvTask;
    TextView tvDeadline;

    public MyViewHolder(View itemView) {
        super(itemView);
        tvTask = itemView.findViewById(R.id.text_done1);
        tvTask.setPaintFlags(tvTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvDeadline = itemView.findViewById(R.id.text_done2);
    }

    public TextView getTvTask() {
        return tvTask;
    }

    public TextView getTvDeadline() {
        return tvDeadline;
    }
}