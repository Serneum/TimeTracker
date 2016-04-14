package com.example.domain.tasks.entry;

import com.example.domain.user.User;

public class TaskEntryBuilder {
    private User user;
    private String description;
    private String dueDate;
    private boolean completed = false;

    public TaskEntryBuilder() {
    }

    public TaskEntryBuilder user(User user) {
        this.user = user;
        return this;
    }

    public TaskEntryBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TaskEntryBuilder dueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public TaskEntryBuilder completed(boolean completed) {
        this.completed = completed;
        return this;
    }

    public TaskEntry build() {
        // This goes against the builder pattern, but we must check the username before trying to create the Key in the Task
        if (getUser() == null) {
            throw new IllegalStateException("A user must be provided");
        }

        // Create the Task
        TaskEntry result = new TaskEntry(this);
        if (dueDate == null) {
            throw new IllegalStateException("A due date must be provided");
        }
        return result;
    }

    protected User getUser() {
        return user;
    }

    protected String getDescription() {
        return description;
    }

    protected String getDueDate() {
        return dueDate;
    }

    protected boolean getCompleted() {
        return completed;
    }
}
