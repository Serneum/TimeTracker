package com.example.tasks;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task>{
    @Override
    public int compare(Task t1, Task t2) {
        int result = 0;
        if (!t1.isCompleted() && t2.isCompleted()) {
            result = -1;
        }
        else if (t1.isCompleted() && !t2.isCompleted()) {
            result = 1;
        }
        else {
            result = t1.getDueDate().compareTo(t2.getDueDate());
        }
        return result;
    }
}
