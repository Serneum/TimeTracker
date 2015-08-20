package com.example.tasks;

public class TaskBuilder {

    private String user;
    private String message;
    private boolean completed = false;

    public TaskBuilder() {
    }

    public TaskBuilder user(String user) {
        this.user = user;
        return this;
    }

    public TaskBuilder message(String message) {
        this.message = message;
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

    protected String getMessage() {
        return message;
    }

    protected boolean getCompleted() {
        return completed;
    }
}
