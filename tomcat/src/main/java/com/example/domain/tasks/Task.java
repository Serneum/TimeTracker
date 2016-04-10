package com.example.domain.tasks;

import com.example.db.Persistent;
import com.example.domain.user.TaskUser;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Task extends Persistent {
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    TaskUser user;

    private Date dueDate;
    private String description;
    private boolean completed = false;

    private Task() {
    }

    Task(UUID id, TaskUser user) {
        this.id = id;
        this.user = user;
    }

    protected Task(TaskBuilder builder) {
        this();
        this.id = UUID.randomUUID();
        this.user = builder.getUser();
        this.description = builder.getDescription();
        setDueDate(builder.getDueDate());
        this.completed = builder.getCompleted();
    }

    public TaskUser getUser() {
        return user;
    }

    public String getUserName() {
        return user.getName();
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getFormattedDueDate() {
        String result = "";
        if (dueDate != null) {
            result = format.format(dueDate);
        }
        return result;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDescription(String description) {
        if (!StringUtils.equals(this.description, description)) {
            this.description = description;
        }
    }

    public void setCompleted(boolean completed) {
        if (this.completed != completed) {
            this.completed = completed;
        }
    }

    public void setDueDate(String dueDate) {
        try {
            this.dueDate = format.parse(dueDate);
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("Unable to parse the provided date");
        }
    }
}
