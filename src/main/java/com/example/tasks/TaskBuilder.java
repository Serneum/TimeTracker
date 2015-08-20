package com.example.tasks;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatException;

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

    public TaskBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder dueDate(String dueDate) {
        try {
            this.dueDate = format.parse(dueDate);
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("Unable to parse the provided date");
        }
        return this;
    }

    public TaskBuilder completed(boolean completed) {
        this.completed = completed;
        return this;
    }

    public Task build() {
        // This goes against the builder pattern, but we must check the username before trying to create the Key in the Task
        if (StringUtils.isBlank(getUser())) {
            throw new IllegalStateException("A user must be provided");
        }

        // Create the Task
        Task result = new Task(this);
        if (dueDate == null) {
            throw new IllegalStateException("A due date must be provided");
        }
        return result;
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
