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
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TasksServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(TasksServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        Key<TaskUser> taskUser = Key.create(TaskUser.class, user.getNickname());
        List<Task> taskList = ObjectifyService.ofy()
                .load()
                .type(Task.class)
                .ancestor(taskUser)
                .order("dueDate")
                .list();

        req.setAttribute("user", user);
        req.setAttribute("taskList", taskList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("tasks.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();  // Find out who the user is.

        String description = getSanitizedValue(req, "description");
        boolean completed = getBoolean(req, "completed");

        Task task = new TaskBuilder()
                .user(user.getNickname())
                .description(description)
                .completed(completed)
                .dueDate("12/31/2015")
                .build();

        ObjectifyService.ofy().save().entity(task).now();
        resp.sendRedirect("/tasks");
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
