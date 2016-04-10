package com.example.domain.tasks;

import com.example.domain.user.TaskUser;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TasksServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TasksServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException {
        TaskDaoSql dao = TaskDaoSql.getInstance();
        TaskUser user = (TaskUser) req.getUserPrincipal();

        String taskId = getSanitizedValue(req, "edit");
        if (StringUtils.isNotBlank(taskId)) {
            Task editTask = dao.restoreForIdAndUser(UUID.fromString(taskId), user.getId());
            if (editTask != null) {
                req.setAttribute("inEditMode", true);
                req.setAttribute("editTask", editTask);
            }
            else {
                logger.log(Level.WARNING, "No task with id '" + taskId + "' found.");
            }
        }

        List<Task> taskList = dao.restoreAllForUser(user.getId());
        Collections.sort(taskList, new TaskComparator());

        String error = getSanitizedValue(req, "error");
        if (StringUtils.isNotBlank(error)) {
            req.setAttribute("error", error);
        }
        req.setAttribute("user", user);
        req.setAttribute("taskList", taskList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("tasks.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
        StringBuilder urlBuilder = new StringBuilder("/tasks");
        TaskDaoSql dao = TaskDaoSql.getInstance();
        TaskUser user = (TaskUser) req.getUserPrincipal();

        String err = "";
        String description = getSanitizedValue(req, "description");
        String dueDate = getSanitizedValue(req, "dueDate");
        boolean completed = getBoolean(req, "completed");
        String taskId = getSanitizedValue(req, "taskId");

        System.err.println(dueDate);

        boolean isNew = false;
        Task task = null;
        if (StringUtils.isBlank(description)) {
            err = "You must enter a description for the task.";
        }
        else {
            try {
                if (StringUtils.isBlank(taskId)) {
                    // Create new entry
                    task = new TaskBuilder()
                            .user(user)
                            .description(description)
                            .completed(completed)
                            .dueDate(dueDate)
                            .build();
                    isNew = true;
                }
                else {
                    // Update existing entry
                    task = dao.restoreForIdAndUser(UUID.fromString(taskId), user.getId());
                    task.setDescription(description);
                    task.setDueDate(dueDate);
                    task.setCompleted(completed);
                }
            }
            catch (IllegalArgumentException e) {
                err = e.getMessage();
            }
        }

        if (StringUtils.isNotBlank(err)) {
            urlBuilder.append("?error=").append(err);
            req.setAttribute("error", err);
        }
        else if (isNew) {
            dao.insert(task);
        }
        else {
            dao.update(task);
        }
        resp.sendRedirect(urlBuilder.toString());
    }

    private String getSanitizedValue(HttpServletRequest req, String param) {
        String val = req.getParameter(param);
        return StringUtils.trimToEmpty(val);
    }

    private boolean getBoolean(HttpServletRequest req, String param) {
        String val = getSanitizedValue(req, param);
        return val.equals("1") || val.equals("yes") || val.equals("on") || Boolean.valueOf(val);
    }
}
