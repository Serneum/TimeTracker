package com.example.domain.project;

import com.example.domain.customer.Customer;
import com.example.domain.user.User;
import org.apache.commons.lang.StringUtils;

public class ProjectBuilder {
    private Customer customer;
    private String name;

    public ProjectBuilder() {
    }

    public ProjectBuilder customer(Customer customer) {
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

    public Customer getCustomer() {
        return customer;
    }

    public String getName() {
        return name;
    }
}
