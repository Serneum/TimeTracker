package com.example.auth;

import com.example.domain.tasks.BaseServlet;
import com.example.domain.user.User;
import com.example.domain.user.UserDaoSql;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("../login.jsp");
        dispatcher.forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        String err = "";
        String username = getSanitizedValue(req, "username");
        String password = getSanitizedValue(req, "password");

        if (StringUtils.isBlank(username)) {
            err = "Username cannot be empty.";
        }
        else if (StringUtils.isBlank(password)) {
            err = "Password cannot be empty.";
        }
        else {
            WebCallbackHandler webCallbackHandler = new WebCallbackHandler(req);
            try {
                LoginContext context = new LoginContext("TaskManager", webCallbackHandler);
                context.login();

                HttpSession session = req.getSession();
                User user = UserDaoSql.getInstance().restoreForName(username);
                session.setAttribute("user", user);
            }
            catch (LoginException e) {
                err = "Invalid credentials. Please try again.";
                e.printStackTrace();
            }
        }

        if (StringUtils.isNotBlank(err)) {
            req.setAttribute("error", err);
            doGet(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/entries/");
    }

}
