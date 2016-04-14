package com.example.domain.user;

import com.example.db.Dao;
import com.example.db.DaoSql;
import com.example.db.Persistent;

import java.sql.ResultSet;
import java.util.UUID;

public class UserDaoSql extends DaoSql<User> implements Dao<User> {

    private static final String TABLE_NAME = "USER";
    private static final String[] COLUMN_DEFINITIONS = new String[] {
            "ID TEXT PRIMARY KEY NOT NULL",
            "PASSWORD TEXT NOT NULL",
            "NAME TEXT UNIQUE",
    };
    private static final String SELECT_FOR_NAME = "SELECT * FROM USER WHERE NAME=?";
    private static final String SELECT_FOR_ID = "SELECT * FROM USER WHERE ID=?";
    private static final String INSERT = "INSERT INTO USER(ID, NAME, PASSWORD) VALUES(?, ?, ?)";
    private static final String UPDATE = "UPDATE USER SET NAME=?, PASSWORD=? WHERE ID=?";
    private static final String DELETE = "DELETE FROM USER WHERE ID=?";

    private static UserDaoSql instance;

    public static UserDaoSql getInstance() {
        if (instance == null) {
            instance = new UserDaoSql();
        }
        return instance;
    }

    public void createTableIfNeeded() {
        super.createTableIfNeeded(TABLE_NAME, COLUMN_DEFINITIONS);

        User user = restoreForName("admin");
        if (user == null) {
            user = new User("admin", "admin");
            insert(user);
        }
    }

    public User restoreForId(UUID id) {
        return super.restore(SELECT_FOR_ID, id.toString());
    }

    public User restoreForName(String name) {
        return super.restore(SELECT_FOR_NAME, name);
    }

    public void insert(Persistent p) {
        User user = (User) p;
        super.update(INSERT, user.getId().toString(),
                             user.getName(),
                             user.getPassword());
    }

    public void update(Persistent p) {
        User user = (User) p;
        super.update(UPDATE, user.getName(),
                             user.getPassword(),
                             user.getId().toString());
    }

    public void delete(Persistent p) {
        User user = (User) p;
        super.update(DELETE, user.getId().toString());
    }

    public User restore(ResultSet rs) {
        User result = null;

        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                String name = rs.getString("NAME");
                String password = rs.getString("PASSWORD");
                result = new User(id, name);
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
