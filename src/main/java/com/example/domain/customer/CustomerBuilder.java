package com.example.domain.customer;

import org.apache.commons.lang.StringUtils;

public class CustomerBuilder {
    private String name;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String State;
    private String zip;
    private String phone1;
    private String phone2;
    private String fax1;
    private String fax2;
    private String email;
    private String website;

    public CustomerBuilder() {
    }

    public CustomerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public CustomerBuilder address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public CustomerBuilder address3(String address3) {
        this.address3 = address3;
        return this;
    }

    public CustomerBuilder city(String city) {
        this.city = city;
        return this;
    }

    public CustomerBuilder state(String state) {
        State = state;
        return this;
    }

    public CustomerBuilder zip(String zip) {
        this.zip = zip;
        return this;
    }

    public CustomerBuilder phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public CustomerBuilder phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public CustomerBuilder fax1(String fax1) {
        this.fax1 = fax1;
        return this;
    }

    public CustomerBuilder fax2(String fax2) {
        this.fax2 = fax2;
        return this;
    }

    public CustomerBuilder email(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder website(String website) {
        this.website = website;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return State;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getFax1() {
        return fax1;
    }

    public String getFax2() {
        return fax2;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public Customer build() {
        // Create the Customer
        Customer result = new Customer(this);
        if (StringUtils.isBlank(getName())) {
            throw new IllegalStateException("A name must be provided");
        }
        return result;
    }
}
