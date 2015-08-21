<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.tasks.Task" %>
<%@ page import="java.util.Date" %>
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
                <th></th>
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
                            <td>${task.description}</td>
                            <td>${task.formattedDueDate}</td>
                            <td>${task.completed}</td>
                            <td><c:if test="${not inEditMode}"><a href="/tasks?edit=${task.id}">Edit</a></c:if></td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </table>

        <br/>
        <p>${inEditMode ? "Edit" : "Create New"} Task</p>
        <c:if test="${not empty error}"><div>${error}</div></c:if>
        <form action="#" method="post">
            <div>Task: <textarea name="description" rows="3" cols="60">${inEditMode ? editTask.description : ""}</textarea></div>
            <div>Due Date: <input type="date" name="dueDate" value="${inEditMode ? editTask.formattedDueDate : ""}"></div>
            <div><input type="checkbox" name="completed" <c:if test="${inEditMode && editTask.completed}">checked="checked"</c:if>/>Completed</div>
            <c:if test="${inEditMode}"><input type="hidden" name="taskId" value="${editTask.id}"></c:if>
            <div><input type="submit" value="Save Task"/></div>
        </form>

    </body>
</html>
