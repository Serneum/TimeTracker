package com.example.domain.user;

import com.example.db.Dao;
import com.example.db.DaoSql;
import com.example.db.Persistent;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TaskUserDaoSql extends DaoSql<TaskUser> implements Dao<TaskUser> {

    private static final String TABLE_NAME = "TASK_USER";
    private static final String[] COLUMN_DEFINITIONS = new String[] {
            "ID TEXT PRIMARY KEY NOT NULL",
            "PASSWORD TEXT NOT NULL",
            "NAME TEXT UNIQUE",
    };
    private static final String SELECT_FOR_NAME = "SELECT * FROM TASK_USER WHERE NAME=?";
    private static final String INSERT = "INSERT INTO TASK_USER(ID, NAME) VALUES(?, ?)";
    private static final String UPDATE = "UPDATE TASK_USER SET NAME=? WHERE ID=?";
    private static final String DELETE = "DELETE FROM TASK_USER WHERE ID=?";

    private static TaskUserDaoSql instance;

    public static TaskUserDaoSql getInstance() {
        if (instance == null) {
            instance = new TaskUserDaoSql();
        }
        return instance;
    }

    public void createTableIfNeeded() {
        super.createTableIfNeeded(TABLE_NAME, COLUMN_DEFINITIONS);
    }

    public TaskUser restoreForName(String name) {
        return super.restore(SELECT_FOR_NAME, name);
    }

    public void insert(Persistent p) {
        TaskUser user = (TaskUser) p;
        super.update(INSERT, user.getId().toString(),
                             user.getName());
    }

    public void update(Persistent p) {
        TaskUser user = (TaskUser) p;
        super.update(UPDATE, user.getName(),
                             user.getId().toString());
    }

    public void delete(Persistent p) {
        TaskUser user = (TaskUser) p;
        super.update(DELETE, user.getId().toString());
    }

    public TaskUser restore(ResultSet rs) {
        TaskUser result = null;

        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                String name = rs.getString("NAME");
                String password = rs.getString("PASSWORD");
                result = new TaskUser(id, name);
                result.setPassword(password);
            }
        }
        catch (Exception e) {
            System.err.println("Error restoring task from database: " + e);
            e.printStackTrace();
        }

        return result;
    }
}
