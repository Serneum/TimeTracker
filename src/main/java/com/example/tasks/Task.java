package com.example.tasks;

import com.example.user.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

import java.util.Date;

@Entity
public class Task {

    @Parent
    Key<User> user;

    @Id
    public Long id;

    @Index
    private Date date;
    private String message;
    private boolean completed = false;

    private Task() {
        this.date = new Date();
    }

    protected Task(TaskBuilder builder) {
        this();
        this.message = builder.getMessage();
        this.completed = builder.getCompleted();
    }

    public String getUser() {
        return user.getName();
    }

    public String getMessage() {
        return message;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Date getDate() {
        return date;
    }
}
