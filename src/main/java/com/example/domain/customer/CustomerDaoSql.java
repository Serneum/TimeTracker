package com.example.domain.customer;

import com.csvreader.CsvReader;
import com.example.db.Dao;
import com.example.db.DaoSql;
import com.example.db.Persistent;
import com.example.domain.user.User;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.server.ExportException;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

public class CustomerDaoSql extends DaoSql<Customer> implements Dao<Customer> {

    private static final String TABLE_NAME = "CUSTOMER";
    private static final String[] COLUMN_DEFINITIONS = new String[] {
            "ID TEXT PRIMARY KEY NOT NULL",
            "NAME TEXT NOT NULL",
            "ADDRESS1 TEXT",
            "ADDRESS2 TEXT",
            "ADDRESS3 TEXT",
            "CITY TEXT",
            "STATE TEXT",
            "ZIP TEXT",
            "PHONE1 TEXT",
            "PHONE2 TEXT",
            "FAX1 TEXT",
            "FAX2 TEXT",
            "EMAIL TEXT",
            "WEBSITE TEXT"
    };
    private static final String SELECT_ALL = "SELECT * FROM CUSTOMER ORDER BY NAME ASC";
    private static final String SELECT_FOR_ID = "SELECT * FROM CUSTOMER WHERE ID=?";
    private static final String SELECT_FOR_NAME = "SELECT * FROM CUSTOMER WHERE NAME=?";
    private static final String INSERT = "INSERT INTO CUSTOMER(ID, NAME, ADDRESS1, ADDRESS2, ADDRESS3, CITY, STATE, ZIP, PHONE1, PHONE2, FAX1, FAX2, EMAIL, WEBSITE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE CUSTOMER SET NAME=?, ADDRESS1=?, ADDRESS2=?, ADDRESS3=?, CITY=?, STATE=?, ZIP=?, PHONE1=?, PHONE2=?, FAX1=?, FAX2=?, EMAIL=?, WEBSITE=? WHERE ID=?";
    private static final String DELETE = "DELETE FROM CUSTOMER WHERE ID=?";

    private static CustomerDaoSql instance;

    public static CustomerDaoSql getInstance() {
        if (instance == null) {
            instance = new CustomerDaoSql();
        }
        return instance;
    }

    public void createTableIfNeeded() {
        super.createTableIfNeeded(TABLE_NAME, COLUMN_DEFINITIONS);

        List<Customer> customers = restoreAll();
        if (customers.isEmpty()) {
            try {
                CsvReader csv = new CsvReader(INSTALL_DIR + "/db/customers.csv");
                csv.readHeaders();

                while (csv.readRecord()) {
                    String name = csv.get("Company");
                    String address1 = csv.get("Address1");
                    String address2 = csv.get("Address2");
                    String address3 = csv.get("Address3");
                    String city = csv.get("City");
                    String state = csv.get("State");
                    String zip = csv.get("Zip");
                    String phone1 = csv.get("Phone1");
                    String phone2 = csv.get("Phone2");
                    String fax1 = csv.get("Fax1");
                    String fax2 = csv.get("Fax2");
                    String email = csv.get("Email");
                    String website = csv.get("Website");

                    Customer customer = new CustomerBuilder().name(name).address1(address1).address2(address2)
                            .address3(address3).city(city).state(state).zip(zip).phone1(phone1).phone2(phone2).fax1(fax1)
                            .fax2(fax2).email(email).website(website).build();
                    customers.add(customer);
                    insert(customer);
                }

                System.err.println("Inserted " + customers.size() + " customers.");
            }
            catch (FileNotFoundException e) {
                System.err.println("Could not find seed data file.");
            }
            catch (Exception e) {
                System.err.println("Error reading from csv file.");
                e.printStackTrace();
            }
        }
    }

    public List<Customer> restoreAll() {
        return super.restoreAll(SELECT_ALL);
    }

    public Customer restoreForId(UUID id) {
        return super.restore(SELECT_FOR_ID, id.toString());
    }

    public Customer restoreForName(String name) {
        return super.restore(SELECT_FOR_NAME, name);
    }

    public void insert(Persistent p) {
        Customer customer = (Customer) p;
        super.update(INSERT, customer.getId().toString(),
                             customer.getName(),
                             customer.getAddress1(),
                             customer.getAddress2(),
                             customer.getAddress3(),
                             customer.getCity(),
                             customer.getState(),
                             customer.getZip(),
                             customer.getPhone1(),
                             customer.getPhone2(),
                             customer.getFax1(),
                             customer.getFax2(),
                             customer.getEmail(),
                             customer.getWebsite());
    }

    public void update(Persistent p) {
        Customer customer = (Customer) p;
        super.update(UPDATE, customer.getName(),
                             customer.getAddress1(),
                             customer.getAddress2(),
                             customer.getAddress3(),
                             customer.getCity(),
                             customer.getState(),
                             customer.getZip(),
                             customer.getPhone1(),
                             customer.getPhone2(),
                             customer.getFax1(),
                             customer.getFax2(),
                             customer.getEmail(),
                             customer.getWebsite(),
                             customer.getId().toString());
    }

    public void delete(Persistent p) {
        Customer customer = (Customer) p;
        super.update(DELETE, customer.getId().toString());
    }

    public Customer restore(ResultSet rs) {
        Customer result = null;

        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                String name = rs.getString("NAME");
                String address1 = rs.getString("ADDRESS1");
                String address2 = rs.getString("ADDRESS2");
                String address3 = rs.getString("ADDRESS3");
                String city = rs.getString("CITY");
                String state = rs.getString("STATE");
                String zip = rs.getString("ZIP");
                String phone1 = rs.getString("PHONE1");
                String phone2 = rs.getString("PHONE2");
                String fax1 = rs.getString("FAX1");
                String fax2 = rs.getString("FAX2");
                String email = rs.getString("EMAIL");
                String website = rs.getString("WEBSITE");

                CustomerBuilder builder = new CustomerBuilder().name(name).address1(address1).address2(address2)
                        .address3(address3).city(city).state(state).zip(zip).phone1(phone1).phone2(phone2).fax1(fax1)
                        .fax2(fax2).email(email).website(website);
                result = new Customer(id, builder);
            }
        }
        catch (Exception e) {
            System.err.println("Error restoring task from database: " + e);
            e.printStackTrace();
        }

        return result;
    }
}
