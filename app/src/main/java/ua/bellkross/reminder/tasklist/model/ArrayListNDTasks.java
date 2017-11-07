package ua.bellkross.reminder.tasklist.model;


import java.util.ArrayList;

public class ArrayListNDTasks extends ArrayList<Task> {

    private static ArrayList<Task> instance;

    private ArrayListNDTasks() {
        instance = new ArrayList<>();
    }

    public static ArrayList<Task> getInstance() {
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
        for (Task item : instance) {
            result += "; " + item.toString();
        }
        return result + " }";
    }
}
