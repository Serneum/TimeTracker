package com.example.domain.tasks;

import org.apache.commons.lang.StringUtils;

public class TaskBuilder {
    private String name;

    public TaskBuilder() {
    }

    public TaskBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Task build() {
        if (StringUtils.isBlank(getName())) {
            throw new IllegalStateException("A name must be provided");
        }
        // Create the Task
        Task result = new Task(this);
        return result;
    }

    protected String getName() {
        return name;
    }
}
