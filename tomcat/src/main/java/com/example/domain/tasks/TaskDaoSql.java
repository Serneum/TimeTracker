package com.example.domain.tasks;

import com.example.db.Dao;
import com.example.db.DaoSql;
import com.example.db.Persistent;
import com.example.domain.user.TaskUser;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDaoSql extends DaoSql implements Dao {

    private static final SimpleDateFormat dbFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
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

    public List<Task> restoreAllForUser(UUID id) {
        List<Task> resultList = new ArrayList<Task>();

        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = getConnection();

            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_FOR_USER);
            stmt.setString(1, id.toString());
            rs = stmt.executeQuery();

            Task target;
            while ((target = restore(rs)) != null) {
                resultList.add(target);
            }
            conn.commit();
            rs.close();
        }
        catch (Exception e) {
            try {
                StringBuilder errBuilder = new StringBuilder(e.getMessage()).append(": Error while executing SQL: ").append(SELECT_ALL_FOR_USER)
                        .append("\n").append(id.toString());
                System.err.println(errBuilder.toString());
                e.printStackTrace();
                if (conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            }
            catch (SQLException sqle) {
                // Swallow
            }

        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    System.err.println("Unable to close connection");
                }
            }
        }

        return resultList;
    }

    public Task restoreForIdAndUser(UUID id, UUID userId) {
        Task result = null;

        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = getConnection();

            PreparedStatement stmt = conn.prepareStatement(SELECT_FOR_ID_AND_USER);
            stmt.setString(1, id.toString());
            stmt.setString(2, userId.toString());
            rs = stmt.executeQuery();

            result = restore(rs);
            conn.commit();
            rs.close();
        }
        catch (Exception e) {
            try {
                StringBuilder errBuilder = new StringBuilder(e.getMessage()).append(": Error while executing SQL: ").append(SELECT_FOR_ID_AND_USER)
                        .append("\n").append(id.toString()).append(", ").append(userId.toString());
                System.err.println(errBuilder.toString());
                e.printStackTrace();
                if (conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            }
            catch (SQLException sqle) {
                // Swallow
            }

        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    System.err.println("Unable to close connection");
                }
            }
        }

        return result;
    }

    public void insert(Persistent p) {
        Task task = (Task) p;
        super.update(INSERT, task.getId().toString(),
                             task.getUser().getId().toString(),
                             task.getDescription(),
                             task.getFormattedDueDate(),
                             String.valueOf(task.isCompleted()));
    }

    public void update(Persistent p) {
        Task task = (Task) p;
        super.update(UPDATE, task.getDescription(),
                             task.getFormattedDueDate(),
                             String.valueOf(task.isCompleted()),
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
                String description = rs.getString("DESCRIPTION");
                String dueDate = rs.getString("DUE_DATE");
                boolean completed = Boolean.valueOf(rs.getString("COMPLETED"));

                UUID userId = UUID.fromString(rs.getString("USER"));
                String userName = rs.getString("NAME");
                TaskUser user = new TaskUser(userId, userName);

                result = new Task(id, user);
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
