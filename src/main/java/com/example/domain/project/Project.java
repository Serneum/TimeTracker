package com.example.domain.project;

import com.example.db.Persistent;
import com.example.domain.customer.Customer;
import com.example.domain.customer.CustomerDaoSql;
import com.example.domain.user.User;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Project extends Persistent {

    private UUID customerId;
    private Customer customer;
    private String name;

    private Project() {
    }

    public Project(UUID id, ProjectBuilder builder) {
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Customer getCustomer() {
        if (customer == null) {
            synchronized (this) {
                if (customer == null) {
                    customer = CustomerDaoSql.getInstance().restoreForId(customerId);
                }
            }
        }
        return customer;
    }

    public void setCustomer(UUID customerId) {
        this.customer = null;
        this.customerId = customerId;
    }
}
