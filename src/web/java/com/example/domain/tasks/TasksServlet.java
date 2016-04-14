package com.example.domain.tasks;

import com.example.domain.user.User;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TasksServlet extends BaseServlet {
    private static final Logger logger = Logger.getLogger(TasksServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException {
        TaskDaoSql dao = TaskDaoSql.getInstance();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String taskId = getSanitizedValue(req, "edit");
        if (StringUtils.isNotBlank(taskId)) {
            Task editTask = null;//dao.restoreForIdAndUser(UUID.fromString(taskId), user.getId());
            if (editTask != null) {
                req.setAttribute("inEditMode", true);
                req.setAttribute("editTask", editTask);
            }
            else {
                logger.log(Level.WARNING, "No task with id '" + taskId + "' found.");
            }
        }

        List<Task> taskList = null; //dao.restoreAllForUser(user.getId());
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
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String err = "";
        String description = getSanitizedValue(req, "description");
        String dueDate = getSanitizedValue(req, "dueDate");
        boolean completed = getBoolean(req, "completed");
        String taskId = getSanitizedValue(req, "taskId");

        boolean isNew = false;
        Task task = null;
        if (StringUtils.isBlank(description)) {
            err = "You must enter a description for the task.";
        }
        else {
            try {
                if (StringUtils.isBlank(taskId)) {
                    // Create new entry
//                    task = new TaskBuilder()
//                            .user(user)
//                            .description(description)
//                            .completed(completed)
//                            .dueDate(dueDate)
//                            .build();
                    isNew = true;
                }
                else {
                    // Update existing entry
//                    task = dao.restoreForIdAndUser(UUID.fromString(taskId), user.getId());
//                    task.setDescription(description);
//                    task.setDueDate(dueDate);
//                    task.setCompleted(completed);
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
}
