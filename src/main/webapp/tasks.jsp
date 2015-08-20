<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.example.tasks.Task" %>
<%@ page import="com.example.user.TaskUser" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="com.googlecode.objectify.cmd.LoadType" %>
<%@ page import="com.googlecode.objectify.cmd.Query" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    </head>

    <body>

    <%
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
            pageContext.setAttribute("user", user);
        }

        List<TaskUser> tasks;
        // Run an ancestor query to ensure we see the most up-to-date
        // view of the Greetings belonging to the selected Guestbook.
        LoadType loadType = ObjectifyService.ofy().load().type(Task.class);
        Query query;
        if (user != null) {
            // Create the correct Ancestor key
            Key<TaskUser> taskUser = Key.create(TaskUser.class, user.getNickname());
            query = loadType.ancestor(taskUser);
            query = query.order("-date");
        }
        else {
            query = loadType.order("-date");
        }
        tasks = query.list();

        pageContext.setAttribute("taskList", tasks);
    %>

    <c:choose>
        <c:when test="${not empty user}">
            <p>Hello, ${fn:escapeXml(user.nickname)}! (You can
                <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
        </c:when>
        <c:otherwise>
            <p>You must be <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">signed in</a>
                to create new tasks.</p>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${not empty user}">
            <p>Tasks for ${user.nickname}</p>
        </c:when>
        <c:otherwise>
            <p>Tasks</p>
        </c:otherwise>
    </c:choose>
    <table>
        <tr>
            <th>Date</th>
            <th>User</th>
            <th>Task</th>
            <th>Completed</th>
        </tr>

        <c:choose>
            <c:when test="${empty taskList}">
                <tr>
                    <td colspan="4">There are no tasks.</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="task" items="${taskList}">
                    <tr>
                        <td>${task.date}</td>
                        <td>${task.user} <c:if test="${user.nickname eq task.user}">(You)</c:if></td>
                        <td>${task.message}</td>
                        <td>${task.completed}</td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </table>

    <br/>
    <p>Create New Task</p>
    <form action="/tasks" method="post">
        <div>Task: <textarea name="description" rows="3" cols="60"></textarea></div>
        <div><input type="checkbox" name="completed"/>Completed</div>
        <div><input type="submit" value="Save Task"/></div>
    </form>

    </body>
</html>
