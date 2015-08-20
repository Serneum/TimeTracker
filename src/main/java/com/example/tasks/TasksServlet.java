package com.example.tasks;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TasksServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
        resp.sendRedirect("tasks.jsp");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();  // Find out who the user is.

        String description = getSanitizedValue(req, "description");
        boolean completed = getBoolean(req, "completed");

        Task task = new TaskBuilder().user(user.getNickname()).description(description).completed(completed).build();

        // Use Objectify to save the greeting and now() is used to make the call synchronously as we
        // will immediately get a new page using redirect and we want the data to be present.
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
