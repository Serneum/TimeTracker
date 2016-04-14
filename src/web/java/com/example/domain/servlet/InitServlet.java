package com.example.domain.servlet;

import com.example.domain.tasks.TaskDaoSql;
import com.example.domain.user.UserDaoSql;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class InitServlet extends HttpServlet {

    //**************************************************************************
    //  CLASS
    //**************************************************************************

    final static private long serialVersionUID = 1L;

    //**************************************************************************
    //  INSTANCE
    //**************************************************************************

    //--------------------------------------------------------------------------
    @Override
    public void init()
    throws ServletException {
        UserDaoSql taskUserDao = new UserDaoSql();
        TaskDaoSql taskDao = new TaskDaoSql();

        taskUserDao.createTableIfNeeded();
        taskDao.createTableIfNeeded();
    }

}
