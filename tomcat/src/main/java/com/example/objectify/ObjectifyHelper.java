package com.example.objectify;

import com.example.domain.tasks.Task;
import com.example.domain.user.TaskUser;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

public class ObjectifyHelper implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        // This will be invoked as part of a warmup request, or the first user request if no warmup
        // request.
//        ObjectifyService.register(TaskUser.class);
//        ObjectifyService.register(Task.class);
    }

    public void contextDestroyed(ServletContextEvent event) {
        // App Engine does not currently invoke this method.
    }
}