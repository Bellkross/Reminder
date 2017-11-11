package ua.bellkross.reminder.tasklist;


import ua.bellkross.reminder.database.DBHelper;
import ua.bellkross.reminder.tasklist.model.Task;

public interface TaskAdapter {

    void filter(String charText);
    void update(Task inputTask, int dbPos);
    default void remove(int dbPos) {
        DBHelper.getInstance().removeFromDB(dbPos + "");
        sort();
    }
    void sort();
    void clearAll();
}
