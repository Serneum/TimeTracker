package com.example.domain.project;

import com.example.db.Persistent;
import com.example.domain.customer.Customer;
import com.example.domain.user.User;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Project extends Persistent {

    private Customer customer;
    private String name;

    private Project() {
    }

    Project(UUID id, ProjectBuilder builder) {
        this(builder);
        this.id = id;
    }

    protected Project(ProjectBuilder builder) {
        this();
        this.id = UUID.randomUUID();
        setName(builder.getName());
        setCustomer(builder.getCustomer());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
