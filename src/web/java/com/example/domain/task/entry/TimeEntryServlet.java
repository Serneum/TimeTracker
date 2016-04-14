package com.example.domain.task.entry;

import com.example.domain.customer.CustomerDaoSql;
import com.example.domain.project.ProjectDaoSql;
import com.example.domain.tasks.TaskDaoSql;
import com.example.domain.tasks.entry.TaskEntry;
import com.example.domain.tasks.entry.TaskEntryBuilder;
import com.example.domain.tasks.entry.TaskEntryDaoSql;
import com.example.domain.tasks.BaseServlet;
import com.example.domain.user.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeEntryServlet extends BaseServlet {
    private static final Logger logger = Logger.getLogger(TimeEntryServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException {
        TaskEntryDaoSql dao = TaskEntryDaoSql.getInstance();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String taskEntry = getSanitizedValue(req, "edit");
        if (StringUtils.isNotBlank(taskEntry)) {
            TaskEntry editEntry = dao.restoreForIdAndUser(UUID.fromString(taskEntry), user.getId());
            if (editEntry != null) {
                req.setAttribute("inEditMode", true);
                req.setAttribute("editEntry", editEntry);
            }
            else {
                logger.log(Level.WARNING, "No task entry with id '" + taskEntry + "' found.");
            }
        }

        String start = getSanitizedValue(req, "start");
        String stop = getSanitizedValue(req, "stop");
        if (StringUtils.isNotBlank(start) || StringUtils.isNotBlank(stop)) {
            String entryId = StringUtils.isNotBlank(start) ? start : stop;
            TaskEntry editEntry = dao.restoreForIdAndUser(UUID.fromString(entryId), user.getId());

            // Set duration first so that we don't get a null start date
            if (StringUtils.isNotBlank(stop)) {
                Date now = new Date();
                Date startDate = editEntry.getStartDate();
                long newDuration = now.getTime() - startDate.getTime();
                editEntry.setDuration(editEntry.getDuration() + newDuration);
            }

            if (StringUtils.isBlank(start)) {
                editEntry.setStartDate(null);
            }
            else {
                editEntry.setStartDate(new Date());
            }
            dao.update(editEntry);
        }

        List<TaskEntry> entryList = dao.restoreAllForUser(user.getId());
        String error = getSanitizedValue(req, "error");
        if (StringUtils.isNotBlank(error)) {
            req.setAttribute("error", error);
        }
        req.setAttribute("user", user);
        req.setAttribute("projectList", ProjectDaoSql.getInstance().restoreAll());
        req.setAttribute("taskList", TaskDaoSql.getInstance().restoreAll());
        req.setAttribute("entryList", entryList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("timeEntries.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
        StringBuilder urlBuilder = new StringBuilder("/entries/");
        TaskEntryDaoSql dao = TaskEntryDaoSql.getInstance();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String err = "";
        String notes = getSanitizedValue(req, "notes");
        String projectId = getSanitizedValue(req, "project");
        String taskId = getSanitizedValue(req, "task");
        String entryId = getSanitizedValue(req, "entryId");

        boolean isNew = false;
        TaskEntry taskEntry = null;
        if (StringUtils.isBlank(projectId)) {
            err = "You must select a project for the entry.";
        }
        if (StringUtils.isBlank(taskId)) {
            err = "You must select a task for the entry.";
        }
        else {
            try {
                if (StringUtils.isBlank(entryId)) {
                    TaskEntry existingEntry = dao.restoreForProjectTaskAndUser(UUID.fromString(projectId),
                            UUID.fromString(taskId), user.getId());
                    if (existingEntry == null) {
                        // Create new entry
                        taskEntry = new TaskEntryBuilder()
                                .user(user.getId())
                                .project(UUID.fromString(projectId))
                                .task(UUID.fromString(taskId))
                                .notes(notes)
                                .build();
                        isNew = true;
                    }
                    else {
                        err = "A time entry already exists for project '" + projectId + "' and task '" + taskId + "'.";
                    }
                }
                else {
                    // Update existing entry
                    taskEntry = dao.restoreForIdAndUser(UUID.fromString(entryId), user.getId());
                    taskEntry.setNotes(notes);
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
            dao.insert(taskEntry);
        }
        else {
            dao.update(taskEntry);
        }
        resp.sendRedirect(urlBuilder.toString());
    }
}
