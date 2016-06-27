package com.bwidlarz.todolist.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2016-06-25.
 */
public class Results {


    private List<Task> tasks = new ArrayList<Task>();

    public Results(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


}
