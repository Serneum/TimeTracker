package com.example.tasks;

import com.example.user.TaskUser;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Query;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;
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
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        String taskId = getSanitizedValue(req, "edit");
        if (StringUtils.isNotBlank(taskId)) {
            Key<TaskUser> taskUser = Key.create(TaskUser.class, user.getNickname());
            Long id = Long.valueOf(taskId);
            Task editTask = ObjectifyService.ofy()
                    .load()
                    .type(Task.class)
                    .parent(taskUser)
                    .id(id)
                    .now();
            if (editTask != null) {
                req.setAttribute("inEditMode", true);
                req.setAttribute("editTask", editTask);
            }
            else {
                logger.log(Level.WARNING, "No task with id '" + id + "' found.");
            }
        }

        Key<TaskUser> taskUser = Key.create(TaskUser.class, user.getNickname());
        List<Task> taskList = ObjectifyService.ofy()
                .load()
                .type(Task.class)
                .ancestor(taskUser)
                .order("dueDate")
                .list();

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
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();  // Find out who the user is.

        String err = "";
        String description = getSanitizedValue(req, "description");
        String dueDate = getSanitizedValue(req, "dueDate");
        boolean completed = getBoolean(req, "completed");
        String taskId = getSanitizedValue(req, "taskId");

        logger.info(taskId);

        Task task = null;
        try {
            if (StringUtils.isBlank(taskId)) {
                // Create new entry
                task = new TaskBuilder()
                        .user(user.getNickname())
                        .description(description)
                        .completed(completed)
                        .dueDate(dueDate)
                        .build();
            }
            else {
                // Update existing entry
                Key<TaskUser> taskUser = Key.create(TaskUser.class, user.getNickname());
                Long id = Long.valueOf(taskId);
                task = ObjectifyService.ofy()
                        .load()
                        .type(Task.class)
                        .parent(taskUser)
                        .id(id)
                        .now();
                task.setDescription(description);
                task.setDueDate(dueDate);
                task.setCompleted(completed);
            }
        }
        catch (IllegalArgumentException e) {
            err = e.getMessage();
        }

        if (StringUtils.isNotBlank(err)) {
            urlBuilder.append("?error=").append(err);
            req.setAttribute("error", err);
        }
        else {
            ObjectifyService.ofy().save().entity(task).now();
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
