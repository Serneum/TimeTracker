package com.example.domain.user;

import com.example.db.Persistent;
import org.jasypt.util.password.BasicPasswordEncryptor;

import java.security.Principal;
import java.util.UUID;

public class User extends Persistent implements Principal {

    private String name;
    private String password;

    public User(UUID id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public User(UUID id, String name, String password) {
        this(id, name);
        this.password = new BasicPasswordEncryptor().encryptPassword(password);
    }

    public User(String name, String password) {
        this(UUID.randomUUID(), name, password);
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
