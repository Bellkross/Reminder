package ua.bellkross.reminder.tasklist.model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static ua.bellkross.reminder.tasklist.TaskListActivity.NOT_DONE_STATE;

public class Task {

    public static final int NEW_ITEM_POSITION = -1;
    private String task;
    private String deadline;
    private Date dateOfDeadline;
    private int done;
    private int positionInList;
    private int positionInDatabase;

    public Task(String task, int positionInList){
        this.task = task;
        this.positionInDatabase = NEW_ITEM_POSITION;
        this.positionInList = positionInList;
        dateOfDeadline = new Date(System.currentTimeMillis());
        //1000 ms = 1 s * 3600 = 1 h * 24 = 1 day * 1 = 1 week
        dateOfDeadline.setTime(dateOfDeadline.getTime() + (1000 * 60 * 60 * 24 * 7));
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        this.deadline = dateFormat.format(dateOfDeadline);
        this.done = NOT_DONE_STATE;
    }

    public Task(String task, String deadline, int positionInList) {
        this.task = task;
        this.deadline = deadline;
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        try {
            this.dateOfDeadline = dateFormat.parse(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.positionInDatabase = NEW_ITEM_POSITION;
        this.positionInList = positionInList;
        this.done = NOT_DONE_STATE;
    }

    public Task(String task, String deadline, int positionInList, int positionInDatabase) {
        this.task = task;
        this.deadline = deadline;
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        try {
            this.dateOfDeadline = dateFormat.parse(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.positionInDatabase = NEW_ITEM_POSITION;
        this.positionInList = positionInList;
        this.positionInDatabase = positionInDatabase;
        this.done = NOT_DONE_STATE;
    }

    public Task(String task, String deadline, int positionInList, int positionInDatabase, int state) {
        this.task = task;
        this.deadline = deadline;
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        try {
            this.dateOfDeadline = dateFormat.parse(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.positionInDatabase = NEW_ITEM_POSITION;
        this.positionInList = positionInList;
        this.positionInDatabase = positionInDatabase;
        this.done = state;
    }

    public Task(String task, String deadline, long dateOfDeadline, int positionInList, int positionInDatabase, int state) {
        this.task = task;
        this.deadline = deadline;
        this.dateOfDeadline = new Date(dateOfDeadline);
        this.positionInDatabase = NEW_ITEM_POSITION;
        this.positionInList = positionInList;
        this.positionInDatabase = positionInDatabase;
        this.done = state;
    }
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getPositionInList() {
        return positionInList;
    }

    public void setPositionInList(int positionInList) {
        this.positionInList = positionInList;
    }

    public int getPositionInDatabase() {
        return positionInDatabase;
    }

    public void setPositionInDatabase(int positionInDatabase) {
        this.positionInDatabase = positionInDatabase;
    }

    public Date getDateOfDeadline() {
        return dateOfDeadline;
    }

    public void setDateOfDeadline(Date dateOfDeadline) {
        this.dateOfDeadline = dateOfDeadline;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task='" + task + '\'' +
                ", deadline='" + deadline + '\'' +
                ", dateOfDeadline=" + dateOfDeadline +
                ", done=" + done +
                ", positionInList=" + positionInList +
                ", positionInDatabase=" + positionInDatabase +
                '}';
    }
}