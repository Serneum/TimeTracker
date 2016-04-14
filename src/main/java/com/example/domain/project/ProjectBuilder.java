package com.example.domain.project;

import com.example.domain.customer.Customer;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

public class ProjectBuilder {
    private UUID customer;
    private String name;

    public ProjectBuilder() {
    }

    public ProjectBuilder customer(UUID customer) {
        this.customer = customer;
        return this;
    }

    public ProjectBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Project build() {
        // Create the Project
        Project result = new Project(this);
        if (getCustomer() == null) {
            throw new IllegalStateException("A customer must be provided");
        }

        if (StringUtils.isBlank(getName())) {
            throw new IllegalStateException("A name must be provided");
        }
        return result;
    }

    public UUID getCustomer() {
        return customer;
    }

    public String getName() {
        return name;
    }
}
