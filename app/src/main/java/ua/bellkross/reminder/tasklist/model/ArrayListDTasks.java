package ua.bellkross.reminder.tasklist.model;


import java.util.ArrayList;

public class ArrayListDTasks extends ArrayList<Task> {

    private static ArrayList<Task> instance;

    private ArrayListDTasks() {
        instance = new ArrayList<>();
    }

    public static ArrayList<Task> getInstance() {
        if (instance == null) {
            new ArrayListDTasks();
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public String toString() {
        String result = "ArrayListDTasks { ";
        for (Task item : instance) {
            result += "; " + item.toString();
        }
        return result + " }";
    }
}