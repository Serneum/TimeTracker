package com.example.domain.project;

import com.example.domain.customer.CustomerDaoSql;
import com.example.domain.tasks.BaseServlet;
import com.example.domain.user.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectsServlet extends BaseServlet {
    private static final Logger logger = Logger.getLogger(ProjectsServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException {
        ProjectDaoSql dao = ProjectDaoSql.getInstance();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String projectId = getSanitizedValue(req, "edit");
        if (StringUtils.isNotBlank(projectId)) {
            Project editProject = dao.restoreForId(UUID.fromString(projectId));
            if (editProject != null) {
                req.setAttribute("inEditMode", true);
                req.setAttribute("editProject", editProject);
            }
            else {
                logger.log(Level.WARNING, "No project with id '" + projectId + "' found.");
            }
        }

        List<Project> projectList = dao.restoreAll();
        String error = getSanitizedValue(req, "error");
        if (StringUtils.isNotBlank(error)) {
            req.setAttribute("error", error);
        }
        req.setAttribute("user", user);
        req.setAttribute("projectList", projectList);
        req.setAttribute("customerList", CustomerDaoSql.getInstance().restoreAll());
        RequestDispatcher dispatcher = req.getRequestDispatcher("projects.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
        StringBuilder urlBuilder = new StringBuilder("/projects/");
        ProjectDaoSql dao = ProjectDaoSql.getInstance();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String err = "";
        String name = getSanitizedValue(req, "name");
        String customerId = getSanitizedValue(req, "customer");
        String projectId = getSanitizedValue(req, "projectId");

        boolean isNew = false;
        Project project = null;
        if (StringUtils.isBlank(customerId)) {
            err = "You must select a customer for the project.";
        }
        if (StringUtils.isBlank(name)) {
            err = "You must enter a name for the project.";
        }
        else {
            try {
                if (StringUtils.isBlank(projectId)) {
                    // Create new entry
                    project = new ProjectBuilder()
                            .customer(UUID.fromString(customerId))
                            .name(name)
                            .build();
                    isNew = true;
                }
                else {
                    // Update existing entry
                    project = dao.restoreForId(UUID.fromString(projectId));
                    project.setName(name);
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
            dao.insert(project);
        }
        else {
            dao.update(project);
        }
        resp.sendRedirect(urlBuilder.toString());
    }
}
