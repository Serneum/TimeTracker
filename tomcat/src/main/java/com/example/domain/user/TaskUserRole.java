package com.example.domain.user;

import java.security.Principal;

public class TaskUserRole implements Principal {

    private String name;

    public TaskUserRole(String name) {
        super();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
