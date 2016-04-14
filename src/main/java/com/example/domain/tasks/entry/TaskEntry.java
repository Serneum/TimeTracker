package com.example.domain.tasks.entry;

import com.example.db.Persistent;
import com.example.domain.project.Project;
import com.example.domain.project.ProjectDaoSql;
import com.example.domain.tasks.Task;
import com.example.domain.tasks.TaskDaoSql;
import com.example.domain.user.User;
import com.example.domain.user.UserDaoSql;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TaskEntry extends Persistent {
    static public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private UUID userId;
    private User user;

    private UUID projectId;
    private Project project;

    private UUID taskId;
    private Task task;

    private String notes;
    private Date startDate;
    private double duration;

    private TaskEntry() {
    }

    TaskEntry(UUID id, TaskEntryBuilder builder) {
        this(builder);
        this.id = id;
    }

    public TaskEntry(TaskEntryBuilder builder) {
        this();
        this.id = UUID.randomUUID();
        setUser(builder.getUser());
        setProject(builder.getProject());
        setTask(builder.getTask());
        setNotes(builder.getNotes());
        setStartDate(builder.getStartDate());
        setDuration(builder.getDuration());
    }

    public UUID getUserId() {
        return userId;
    }

    public User getUser() {
        if (user == null) {
            synchronized (this) {
                if (user == null) {
                    user = UserDaoSql.getInstance().restoreForId(userId);
                }
            }
        }
        return user;
    }

    private void setUser(UUID userId) {
        this.user = null;
        this.userId = userId;
    }

    public String getUserName() {
        return getUser().getName();
    }

    public UUID getProjectId() {
        return projectId;
    }

    public Project getProject() {
        if (project == null) {
            synchronized (this) {
                if (project == null) {
                    project = ProjectDaoSql.getInstance().restoreForId(projectId);
                }
            }
        }
        return project;
    }

    private void setProject(UUID projectId) {
        this.project = null;
        this.projectId = projectId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public Task getTask() {
        if (task == null) {
            synchronized (this) {
                if (task == null) {
                    task = TaskDaoSql.getInstance().restoreForId(taskId);
                }
            }
        }
        return task;
    }

    private void setTask(UUID taskId) {
        this.task = null;
        this.taskId = taskId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return this.notes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getFormattedStartDate() {
        String result = "";
        if (startDate != null) {
            result = format.format(startDate);
        }
        return result;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
