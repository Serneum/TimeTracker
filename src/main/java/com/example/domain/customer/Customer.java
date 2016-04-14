package com.example.domain.customer;

import com.example.db.Persistent;
import com.example.domain.user.User;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class Customer extends Persistent {
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    User user;

    private UUID id;
    private String name;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String State;
    private int zip;
    private String phone1;
    private String phone2;
    private String fax1;
    private String fax2;
    private String email;
    private String website;


    private Customer() {
    }

    Customer(UUID id, CustomerBuilder builder) {
        this(builder);
        this.id = id;
    }

    public Customer(CustomerBuilder builder) {
        this();
        this.id = UUID.randomUUID();
        this.user = builder.getUser();
        setName(builder.getName());
        setAddress1(builder.getAddress1());
        setAddress2(builder.getAddress2());
        setAddress3(builder.getAddress3());
        setCity(builder.getCity());
        setState(builder.getState());
        setZip(builder.getZip());
        setPhone1(builder.getPhone1());
        setPhone2(builder.getPhone2());
        setFax1(builder.getFax1());
        setFax2(builder.getFax2());
        setEmail(builder.getEmail());
        setWebsite(builder.getWebsite());
    }

    public User getUser() {
        return user;
    }

    public String getUserName() {
        return user.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax1() {
        return fax1;
    }

    public void setFax1(String fax1) {
        this.fax1 = fax1;
    }

    public String getFax2() {
        return fax2;
    }

    public void setFax2(String fax2) {
        this.fax2 = fax2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

//    public void setDueDate(String dueDate) {
//        try {
//            this.dueDate = format.parse(dueDate);
//        }
//        catch (ParseException e) {
//            throw new IllegalArgumentException("Unable to parse the provided date");
//        }
//    }
}
