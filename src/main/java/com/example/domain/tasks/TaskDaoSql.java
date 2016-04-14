package com.example.domain.tasks;

import com.example.db.Dao;
import com.example.db.DaoSql;
import com.example.db.Persistent;
import com.example.domain.user.User;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

public class TaskDaoSql extends DaoSql<Task> implements Dao<Task> {

    private static final String TABLE_NAME = "TASK";
    private static final String[] COLUMN_DEFINITIONS = new String[] {
            "ID TEXT PRIMARY KEY NOT NULL",
            "NAME TEXT"
    };
    private static final String SELECT_ALL = "SELECT * FROM TASK ORDER BY NAME ASC";
    private static final String SELECT_FOR_ID = "SELECT * FROM TASK WHERE ID=?";
    private static final String INSERT = "INSERT INTO TASK(ID, NAME) VALUES(?, ?)";
    private static final String UPDATE = "UPDATE TASK SET NAME=? WHERE ID=?";
    private static final String DELETE = "DELETE FROM TASK WHERE ID=?";

    private static TaskDaoSql instance;

    public static TaskDaoSql getInstance() {
        if (instance == null) {
            instance = new TaskDaoSql();
        }
        return instance;
    }

    public void createTableIfNeeded() {
        super.createTableIfNeeded(TABLE_NAME, COLUMN_DEFINITIONS);
    }

    public List<Task> restoreAll() {
        return super.restoreAll(SELECT_ALL);
    }

    public Task restoreForId(UUID id) {
        return super.restore(SELECT_FOR_ID, id.toString());
    }

    public void insert(Persistent p) {
        Task task = (Task) p;
        super.update(INSERT, task.getId().toString(),
                             task.getName());
    }

    public void update(Persistent p) {
        Task task = (Task) p;
        super.update(UPDATE, task.getName(),
                             task.getId().toString());
    }

    public void delete(Persistent p) {
        Task task = (Task) p;
        super.update(DELETE, task.getId().toString());
    }

    public Task restore(ResultSet rs) {
        Task result = null;

        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                String name = rs.getString("NAME");

                TaskBuilder builder = new TaskBuilder().name(name);
                result = new Task(id, builder);
            }
        }
        catch (Exception e) {
            System.err.println("Error restoring task from database: " + e);
            e.printStackTrace();
        }

        return result;
    }
}
