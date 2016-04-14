package com.example.domain.tasks.entry;

import com.example.domain.project.Project;
import com.example.domain.tasks.Task;
import com.example.domain.user.User;

import java.util.Date;
import java.util.UUID;

public class TaskEntryBuilder {
    private UUID user;
    private UUID project;
    private UUID task;
    private String notes;
    private Date startDate;
    private long duration;

    public TaskEntryBuilder() {
    }

    public TaskEntryBuilder user(UUID user) {
        this.user = user;
        return this;
    }

    public TaskEntryBuilder project(UUID project) {
        this.project = project;
        return this;
    }

    public TaskEntryBuilder task(UUID task) {
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

    public TaskEntryBuilder duration(long duration) {
        this.duration = duration;
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
        return result;
    }

    public UUID getUser() {
        return user;
    }

    public UUID getProject() {
        return project;
    }

    public UUID getTask() {
        return task;
    }

    public String getNotes() {
        return notes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public long getDuration() {
        return duration;
    }
}
