package com.example.domain.customer;

import com.example.db.Dao;
import com.example.db.DaoSql;
import com.example.db.Persistent;
import com.example.domain.user.User;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

public class CustomerDaoSql extends DaoSql<Customer> implements Dao<Customer> {

    private static final String TABLE_NAME = "TASK";
    private static final String[] COLUMN_DEFINITIONS = new String[] {
            "ID TEXT PRIMARY KEY NOT NULL",
            "USER TEXT NOT NULL",
            "DESCRIPTION TEXT",
            "DUE_DATE TEXT NOT NULL",
            "COMPLETED TEXT NOT NULL",
            "FOREIGN KEY(USER) REFERENCES TASK_USER(ID)"
    };
    private static final String SELECT_ALL_FOR_USER = "SELECT * FROM TASK INNER JOIN TASK_USER AS TU ON TU.ID = USER WHERE USER=? ORDER BY DUE_DATE ASC";
    private static final String SELECT_FOR_ID_AND_USER = "SELECT * FROM TASK INNER JOIN TASK_USER AS TU ON TU.ID = USER WHERE TASK.ID=? AND USER=? ORDER BY DUE_DATE ASC";
    private static final String INSERT = "INSERT INTO TASK(ID, USER, DESCRIPTION, DUE_DATE, COMPLETED) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE TASK SET DESCRIPTION=?, DUE_DATE=?, COMPLETED=? WHERE ID=?";
    private static final String DELETE = "DELETE FROM TASK WHERE ID=?";

    private static CustomerDaoSql instance;

    public static CustomerDaoSql getInstance() {
        if (instance == null) {
            instance = new CustomerDaoSql();
        }
        return instance;
    }

    public void createTableIfNeeded() {
        super.createTableIfNeeded(TABLE_NAME, COLUMN_DEFINITIONS);
    }

    public List<Customer> restoreAllForUser(UUID id) {
        return super.restoreAll(SELECT_ALL_FOR_USER, id.toString());
    }

    public Customer restoreForIdAndUser(UUID id, UUID userId) {
        return super.restore(SELECT_FOR_ID_AND_USER, id.toString(), userId.toString());
    }

    public void insert(Persistent p) {
        Customer customer = (Customer) p;
        super.update(INSERT, customer.getId().toString(),
                             customer.getUser().getId().toString(),
                             customer.getDescription(),
                             customer.getFormattedDueDate(),
                             String.valueOf(customer.isCompleted()));
    }

    public void update(Persistent p) {
        Customer customer = (Customer) p;
        super.update(UPDATE, customer.getDescription(),
                             customer.getFormattedDueDate(),
                             String.valueOf(customer.isCompleted()),
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
                String description = rs.getString("DESCRIPTION");
                String dueDate = rs.getString("DUE_DATE");
                boolean completed = Boolean.valueOf(rs.getString("COMPLETED"));

                UUID userId = UUID.fromString(rs.getString("USER"));
                String userName = rs.getString("NAME");
                String password = rs.getString("PASSWORD");
                User user = new User(userId, userName);
                user.setPassword(password);

                result = new Customer(id, user);
                result.setDescription(description);
                result.setDueDate(dueDate);
                result.setCompleted(completed);
            }
        }
        catch (Exception e) {
            System.err.println("Error restoring task from database: " + e);
            e.printStackTrace();
        }

        return result;
    }
}
