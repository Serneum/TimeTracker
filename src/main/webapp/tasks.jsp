<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    </head>

    <%
        UserService userService = UserServiceFactory.getUserService();
    %>

    <body>
        <p>Hello, ${fn:escapeXml(user.nickname)}! (You can
            <a href="<%= userService.createLogoutURL("/tasks") %>">sign out</a>.)</p>

        <p>Tasks</p>
        <table>
            <tr>
                <th>Description</th>
                <th>Due Date</th>
                <th>Completed</th>
            </tr>

            <c:choose>
                <c:when test="${empty taskList}">
                    <tr>
                        <td colspan="3">There are no tasks.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="task" items="${taskList}">
                        <tr>
                            <td>${task.description}</td>
                            <td><fmt:formatDate pattern="MM/dd/yyyy" value="${task.dueDate}"/></td>
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
