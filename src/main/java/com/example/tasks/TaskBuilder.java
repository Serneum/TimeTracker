package com.example.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskBuilder {

    private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    private String user;
    private String description;
    private Date dueDate;
    private boolean completed = false;

    public TaskBuilder() {
    }

    public TaskBuilder user(String user) {
        this.user = user;
        return this;
    }

    public TaskBuilder message(String message) {
        this.description = message;
        return this;
    }

    public TaskBuilder dueDate(String dueDate)
    throws ParseException {
        this.dueDate = format.parse(dueDate);
        return this;
    }

    public TaskBuilder completed(boolean completed) {
        this.completed = completed;
        return this;
    }

    public Task build() {
        return new Task(this);
    }

    protected String getUser() {
        return user;
    }

    protected String getDescription() {
        return description;
    }

    protected Date getDueDate() {
        return dueDate;
    }

    protected boolean getCompleted() {
        return completed;
    }
}
