package com.example.domain.tasks;

import com.example.db.Persistent;
import com.example.domain.user.User;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Task extends Persistent {
    private String name;

    private Task() {
    }

    public Task(UUID id, TaskBuilder builder) {
        this(builder);
        this.id = id;
    }

    protected Task(TaskBuilder builder) {
        this();
        this.id = UUID.randomUUID();
        setName(builder.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
