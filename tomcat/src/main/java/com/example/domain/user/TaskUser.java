package com.example.domain.user;

import com.example.db.Persistent;

import java.security.Principal;
import java.util.UUID;

public class TaskUser extends Persistent implements Principal {

    private String name;

    public TaskUser(UUID id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public TaskUser(String name) {
        super();
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
