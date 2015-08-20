package com.example.tasks;

import com.example.user.TaskUser;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

import java.util.Date;

@Entity
public class Task {

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
        this.dueDate = builder.getDueDate();
        this.completed = builder.getCompleted();
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

    public Date getDueDate() {
        return dueDate;
    }
}
