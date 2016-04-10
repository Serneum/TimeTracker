package com.example.user;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class TaskUser {

    @Id
    private String name;

    public TaskUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
