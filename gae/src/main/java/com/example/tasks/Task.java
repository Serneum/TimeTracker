package com.example.tasks;

import com.example.user.TaskUser;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Task {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Parent
    Key<TaskUser> user;

    @Id
    public Long id;

    @Index
    private Date dueDate;
    private String description;
    private boolean completed = false;

    private Task() {
    }

    protected Task(TaskBuilder builder) {
        this();
        this.user = Key.create(TaskUser.class, builder.getUser());
        this.description = builder.getDescription();
        setDueDate(builder.getDueDate());
        this.completed = builder.getCompleted();
    }

    public Long getId() {
        return id;
    }

    public String getUser() {
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
