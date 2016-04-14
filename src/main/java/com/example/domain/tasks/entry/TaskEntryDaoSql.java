package com.example.domain.tasks.entry;

import com.example.db.Dao;
import com.example.db.DaoSql;
import com.example.db.Persistent;
import com.example.domain.project.Project;
import com.example.domain.project.ProjectBuilder;
import com.example.domain.tasks.Task;
import com.example.domain.tasks.TaskBuilder;
import com.example.domain.user.User;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskEntryDaoSql extends DaoSql<TaskEntry> implements Dao<TaskEntry> {

    private static final String TABLE_NAME = "TASK_ENTRY";
    private static final String[] COLUMN_DEFINITIONS = new String[] {
            "ID TEXT NOT NULL",
            "USER TEXT NOT NULL",
            "PROJECT TEXT NOT NULL",
            "TASK TEXT NOT NULL",
            "NOTES TEXT",
            "START_DATE TEXT",
            "DURATION REAL",
            "FOREIGN KEY(USER) REFERENCES USER(ID)",
            "FOREIGN KEY(PROJECT) REFERENCES PROJECT(ID)",
            "FOREIGN KEY(TASK) REFERENCES TASK(ID)",
            "PRIMARY KEY(USER, PROJECT, TASK)"
    };
    private static final String SELECT_ALL_FOR_USER = "SELECT * FROM TASK_ENTRY INNER JOIN USER U ON U.ID = USER WHERE USER=?";
    private static final String SELECT_FOR_ID_AND_USER = "SELECT * FROM TASK_ENTRY INNER JOIN USER U ON U.ID = USER WHERE TASK.ID=? AND USER=?";
    private static final String INSERT = "INSERT INTO TASK_ENTRY(ID, USER, PROJECT, TASK, NOTES, START_DATE, DURATION) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE TASK_ENTRY SET NOTES=?, START_DATE=NULL, DURATION=? WHERE ID=?";
    private static final String DELETE = "DELETE FROM TASK_ENTRY WHERE ID=?";

    private static TaskEntryDaoSql instance;

    public static TaskEntryDaoSql getInstance() {
        if (instance == null) {
            instance = new TaskEntryDaoSql();
        }
        return instance;
    }

    public void createTableIfNeeded() {
        super.createTableIfNeeded(TABLE_NAME, COLUMN_DEFINITIONS);
    }

    public List<TaskEntry> restoreAllForUser(UUID id) {
        return super.restoreAll(SELECT_ALL_FOR_USER, id.toString());
    }

    public TaskEntry restoreForIdAndUser(UUID id, UUID userId) {
        return super.restore(SELECT_FOR_ID_AND_USER, id.toString(), userId.toString());
    }

    public void insert(Persistent p) {
        TaskEntry taskEntry = (TaskEntry) p;
        super.update(INSERT, taskEntry.getId().toString(),
                             taskEntry.getUserId().toString(),
                             taskEntry.getProjectId().toString(),
                             taskEntry.getTaskId().toString(),
                             taskEntry.getNotes(),
                             taskEntry.getFormattedStartDate(),
                             String.valueOf(taskEntry.getDuration()));
    }

    public void update(Persistent p) {
        TaskEntry taskEntry = (TaskEntry) p;
        super.update(UPDATE, taskEntry.getNotes(),
                             String.valueOf(taskEntry.getDuration()),
                             taskEntry.getId().toString());
    }

    public void delete(Persistent p) {
        TaskEntry taskEntry = (TaskEntry) p;
        super.update(DELETE, taskEntry.getId().toString());
    }

    public TaskEntry restore(ResultSet rs) {
        TaskEntry result = null;

        try {
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("ID"));
                UUID userId = UUID.fromString(rs.getString("USER"));
                UUID projectId = UUID.fromString(rs.getString("PROJECT"));
                UUID taskId = UUID.fromString(rs.getString("TASK"));
                String notes = rs.getString("NOTES");
                Date startDate = rs.getDate("START_DATE");
                double duration = rs.getDouble("DURATION");

                TaskEntryBuilder builder = new TaskEntryBuilder().user(userId).project(projectId).task(taskId)
                        .notes(notes).startDate(startDate).duration(duration);
                result = new TaskEntry(id, builder);

            }
        }
        catch (Exception e) {
            System.err.println("Error restoring task entry from database: " + e);
            e.printStackTrace();
        }

        return result;
    }
}
