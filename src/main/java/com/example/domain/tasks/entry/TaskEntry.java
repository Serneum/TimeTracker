package com.example.domain.tasks.entry;

import com.example.db.Persistent;
import com.example.domain.project.Project;
import com.example.domain.tasks.Task;
import com.example.domain.user.User;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TaskEntry extends Persistent {
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private User user;
    private Project project;
    private Task task;
    private String notes;
    private Date startDate;
    private Date endDate;

    private TaskEntry() {
    }

    TaskEntry(UUID id, TaskEntryBuilder builder) {
        this(builder);
        this.id = id;
    }

    public TaskEntry(TaskEntryBuilder builder) {
        this();
        this.id = UUID.randomUUID();
    }

    public User getUser() {
        return user;
    }

    public String getUserName() {
        return user.getName();
    }

    public Project getProject() {
        return project;
    }

    public TaskEntry setProject(Project project) {
        this.project = project;
        return this;
    }

    public Task getTask() {
        return task;
    }

    public TaskEntry setTask(Task task) {
        this.task = task;
        return this;
    }

    public TaskEntry setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public TaskEntry setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public TaskEntry setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }


//    public String getFormattedDueDate() {
//        String result = "";
//        if (dueDate != null) {
//            result = format.format(dueDate);
//        }
//        return result;
//    }
//
//
//    public void setDueDate(String dueDate) {
//        try {
//            this.dueDate = format.parse(dueDate);
//        }
//        catch (ParseException e) {
//            throw new IllegalArgumentException("Unable to parse the provided date");
//        }
//    }
}
