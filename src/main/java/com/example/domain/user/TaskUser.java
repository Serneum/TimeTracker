package com.example.domain.user;

import com.example.db.Persistent;
import org.jasypt.util.password.BasicPasswordEncryptor;

import java.security.Principal;
import java.util.UUID;

public class TaskUser extends Persistent implements Principal {

    private String name;
    private String password;

    public TaskUser(UUID id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public TaskUser(UUID id, String name, String password) {
        this(id, name);
        this.password = new BasicPasswordEncryptor().encryptPassword(password);
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
