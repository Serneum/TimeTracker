package com.example.domain.servlet;

import com.example.domain.customer.CustomerDaoSql;
import com.example.domain.project.ProjectDaoSql;
import com.example.domain.tasks.TaskDaoSql;
import com.example.domain.tasks.entry.TaskEntryDaoSql;
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
        UserDaoSql.getInstance().createTableIfNeeded();
        CustomerDaoSql.getInstance().createTableIfNeeded();
        ProjectDaoSql.getInstance().createTableIfNeeded();
        TaskDaoSql.getInstance().createTableIfNeeded();
        TaskEntryDaoSql.getInstance().createTableIfNeeded();
    }

}
