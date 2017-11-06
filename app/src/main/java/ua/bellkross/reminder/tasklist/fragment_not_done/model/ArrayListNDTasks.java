package ua.bellkross.reminder.tasklist.fragment_not_done.model;


import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

import ua.bellkross.reminder.database.DBHelper;

public class ArrayListNDTasks extends ArrayList<TaskNotDone> {

    private static ArrayList<TaskNotDone> instance;

    private ArrayListNDTasks() {
        instance = new ArrayList<>();
    }

    public static ArrayList<TaskNotDone> getInstance() {
        if (instance == null) {
            new ArrayListNDTasks();
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public String toString() {
        String result = "ArrayListNDTasks { ";
        for (TaskNotDone item : instance) {
            result += "; " + item.toString();
        }
        return result + " }";
    }
}
