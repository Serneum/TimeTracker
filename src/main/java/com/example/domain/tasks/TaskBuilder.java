package com.example.domain.tasks;

import com.example.domain.user.TaskUser;

public class TaskBuilder {
    private TaskUser user;
    private String description;
    private String dueDate;
    private boolean completed = false;

    public TaskBuilder() {
    }

    public TaskBuilder user(TaskUser user) {
        this.user = user;
        return this;
    }

    public TaskBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder dueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public TaskBuilder completed(boolean completed) {
        this.completed = completed;
        return this;
    }

    public Task build() {
        // This goes against the builder pattern, but we must check the username before trying to create the Key in the Task
        if (getUser() == null) {
            throw new IllegalStateException("A user must be provided");
        }

        // Create the Task
        Task result = new Task(this);
        if (dueDate == null) {
            throw new IllegalStateException("A due date must be provided");
        }
        return result;
    }

    protected TaskUser getUser() {
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
