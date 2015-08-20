package com.example.tasks;

public class TaskBuilder {

    private String message;
    private boolean completed = false;

    public TaskBuilder() {
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

    protected String getMessage() {
        return message;
    }

    protected boolean getCompleted() {
        return completed;
    }
}
