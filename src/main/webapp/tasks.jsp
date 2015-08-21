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
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <!-- Latest minified JQuery -->
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <%
        UserService userService = UserServiceFactory.getUserService();
        Date now = new Date();
        pageContext.setAttribute("today", now);
    %>

    <body>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                Hello, ${fn:escapeXml(user.nickname)}!
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="<%= userService.createLogoutURL("/") %>">Sign Out</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading">Tasks</div>
                <table class="table">
                    <tr>
                        <th style="width: 10%">#</th>
                        <th style="width: 50%">Description</th>
                        <th style="width: 20%">Due Date</th>
                        <th style="width: 10%">Completed</th>
                        <th style="width: 10%"></th>
                    </tr>

                    <c:choose>
                        <c:when test="${empty taskList}">
                            <tr>
                                <td colspan="5" style="text-align: center">There are no tasks.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="task" items="${taskList}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${task.description}</td>
                                    <td>${task.formattedDueDate}</td>
                                    <td>${task.completed}</td>
                                    <td>
                                        <c:if test="${not inEditMode}">
                                            <a href="/tasks?edit=${task.id}" class="btn" role="button">
                                                <span class="glyphicon glyphicon-pencil"> Edit</span>
                                            </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </table>
            </div>

            <br/>
            <c:if test="${not empty error}"><div>${error}</div></c:if>

            <form action="#" method="post">
                <div class="panel panel-default">
                    <div class="panel-heading">${inEditMode ? "Edit" : "Create New"} Task</div>
                    <table class="table">
                        <tr>
                            <td>Task:</td>
                            <td><textarea name="description" rows="3" cols="60">${inEditMode ? editTask.description : ""}</textarea></td>
                        </tr>
                        <tr>
                            <td>Due Date:</td>
                            <td><input type="date" name="dueDate" value="${inEditMode ? editTask.formattedDueDate : ""}"></td>
                        </tr>
                        <tr>
                            <td>Completed:</td>
                            <td><input type="checkbox" name="completed" <c:if test="${inEditMode && editTask.completed}">checked="checked"</c:if>/></td>
                        </tr>
                        <c:if test="${inEditMode}">
                            <input type="hidden" name="taskId" value="${editTask.id}">
                        </c:if>
                    </table>
                </div>
                <button type="submit" class="btn btn-primary">
                    <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span>
                    Save Task
                </button>
            </form>
        </div>
    </body>
</html>
