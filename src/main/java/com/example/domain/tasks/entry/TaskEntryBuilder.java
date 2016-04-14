package com.example.domain.tasks.entry;

import com.example.domain.project.Project;
import com.example.domain.tasks.Task;
import com.example.domain.user.User;

import java.util.Date;

public class TaskEntryBuilder {
    private User user;
    private Project project;
    private Task task;
    private String notes;
    private Date startDate;
    private Date endDate;

    public TaskEntryBuilder() {
    }

    public TaskEntryBuilder user(User user) {
        this.user = user;
        return this;
    }

    public TaskEntryBuilder project(Project project) {
        this.project = project;
        return this;
    }

    public TaskEntryBuilder task(Task task) {
        this.task = task;
        return this;
    }

    public TaskEntryBuilder notes(String notes) {
        this.notes = notes;
        return this;
    }

    public TaskEntryBuilder startDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public TaskEntryBuilder endDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public TaskEntry build() {
        // Create the TaskEntry
        TaskEntry result = new TaskEntry(this);
        if (getUser() == null) {
            throw new IllegalStateException("A user must be provided");
        }

        if (getProject() == null) {
            throw new IllegalStateException("A project must be provided");
        }

        if (getTask() == null) {
            throw new IllegalStateException("A task must be provided");
        }

        if (getStartDate() == null) {
            throw new IllegalStateException("A start date must be provided");
        }
        return result;
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }

    public Task getTask() {
        return task;
    }

    public String getNotes() {
        return notes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date endDate() {
        return endDate;
    }
}
