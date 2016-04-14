package com.example.domain.project;

import com.example.db.Dao;
import com.example.db.DaoSql;
import com.example.db.Persistent;
import com.example.domain.user.User;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

public class ProjectDaoSql extends DaoSql<Project> implements Dao<Project> {

    private static final String TABLE_NAME = "PROJECT";
    private static final String[] COLUMN_DEFINITIONS = new String[] {
            "ID TEXT PRIMARY KEY NOT NULL",
            "CUSTOMER TEXT NOT NULL",
            "NAME TEXT NOT NULL",
            "FOREIGN KEY(CUSTOMER) REFERENCES CUSTOMER(ID)"
    };
    private static final String SELECT_ALL = "SELECT * FROM PROJECT ORDER BY NAME ASC";
    private static final String SELECT_FOR_ID = "SELECT * FROM PROJECT WHERE ID=? ORDER BY NAME ASC";
    private static final String INSERT = "INSERT INTO PROJECT(ID, CUSTOMER, NAME) VALUES(?, ?, ?)";
    private static final String UPDATE = "UPDATE PROJECT SET NAME=? WHERE ID=?";
    private static final String DELETE = "DELETE FROM PROJECT WHERE ID=?";

    private static ProjectDaoSql instance;

    public static ProjectDaoSql getInstance() {
        if (instance == null) {
            instance = new ProjectDaoSql();
        }
        return instance;
    }

    public void createTableIfNeeded() {
        super.createTableIfNeeded(TABLE_NAME, COLUMN_DEFINITIONS);
    }

    public List<Project> restoreAll() {
        return super.restoreAll(SELECT_ALL);
    }

    public Project restoreForId(UUID id) {
        return super.restore(SELECT_FOR_ID, id.toString());
    }

    public void insert(Persistent p) {
        Project project = (Project) p;
        super.update(INSERT, project.getId().toString(),
                             project.getCustomerId().toString(),
                             project.getName());
    }

    public void update(Persistent p) {
        Project project = (Project) p;
        super.update(UPDATE, project.getName(),
                             project.getId().toString());
    }

    public void delete(Persistent p) {
        Project project = (Project) p;
        super.update(DELETE, project.getId().toString());
    }

    public Project restore(ResultSet rs) {
        Project result = null;

        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                UUID customerId = UUID.fromString(rs.getString("CUSTOMER"));
                String name = rs.getString("NAME");

                ProjectBuilder builder = new ProjectBuilder().customer(customerId).name(name);
                result = new Project(id, builder);
            }
        }
        catch (Exception e) {
            System.err.println("Error restoring project from database: " + e);
            e.printStackTrace();
        }

        return result;
    }
}
