package ua.bellkross.reminder.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import ua.bellkross.reminder.tasklist.fragment_not_done.model.ArrayListNDTasks;
import ua.bellkross.reminder.tasklist.fragment_not_done.model.TaskNotDone;

import static ua.bellkross.reminder.tasklist.TaskListActivity.LOG_TAG;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "BELLDB";
    public static final String TABLE_NAME = "BELLTABLE";
    public static String ID_TAG = "ID";
    public static String POSITION_IN_LIST_TAG = "POSITION_IN_LIST";
    public static String TASK_TAG = "TASK";
    public static String DEADLINE_TAG = "DEADLINE";
    public static String DEADLINE_DATE_TAG = "DEADLINE_DATE";
    public static String STATE_TAG = "DONE";
    public static DBHelper instance;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        instance = this;
    }

    public int addInDB(TaskNotDone task) {
        SQLiteDatabase db = getWritableDatabase();
        db.query(TABLE_NAME, null, null, null, null, null, DEADLINE_TAG);
        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_IN_LIST_TAG, task.getPositionInList());
        contentValues.put(TASK_TAG, task.getTask());
        contentValues.put(DEADLINE_TAG, task.getDeadline());
        contentValues.put(DEADLINE_DATE_TAG, task.getDateOfDeadline().getTime());
        contentValues.put(STATE_TAG, task.getDone());
        int position = (int) db.insert(TABLE_NAME, null, contentValues);
        db.close();
        contentValues.clear();

        return position;
    }

    public ArrayList<TaskNotDone> sortTasks() {
        int positionInList = -1;
        ArrayList<TaskNotDone> tasks = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, DEADLINE_DATE_TAG);
        if (cursor.moveToFirst()) {
            int taskIDindex = cursor.getColumnIndex(ID_TAG);
            int taskIndex = cursor.getColumnIndex(TASK_TAG);
            int deadlineDateIndex = cursor.getColumnIndex(DEADLINE_DATE_TAG);
            int deadlineIndex = cursor.getColumnIndex(DEADLINE_TAG);
            int stateIndex = cursor.getColumnIndex(STATE_TAG);
            do {
                tasks.add(new TaskNotDone(cursor.getString(taskIndex), cursor.getString(deadlineIndex),
                        cursor.getLong(deadlineDateIndex), ++positionInList, cursor.getInt(taskIDindex),
                        cursor.getInt(stateIndex)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public void removeFromDB(String id) {
        if (!id.equals("")) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_NAME, ID_TAG + " = " + id, null);
            db.close();
        }
    }

    public void clearAll() {
        SQLiteDatabase db = getWritableDatabase();
        for (TaskNotDone item: ArrayListNDTasks.getInstance()) {
            db.delete(TABLE_NAME, ID_TAG + " = " + item.getPositionInDatabase(), null);
        }
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                ID_TAG + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                POSITION_IN_LIST_TAG + " INTEGER NOT NULL," +
                TASK_TAG + " TEXT NOT NULL," +
                DEADLINE_TAG + " TEXT NOT NULL," +
                DEADLINE_DATE_TAG + " DATE NOT NULL," +
                STATE_TAG + " BOOLEAN NOT NULL" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static DBHelper getInstance(Context context) {
        return instance == null ? new DBHelper(context) : instance;
    }

    public static DBHelper getInstance() {
        return instance;
    }
}