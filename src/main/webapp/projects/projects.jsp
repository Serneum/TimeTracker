<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.domain.tasks.Task" %>
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

    <body>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                Hello, ${fn:escapeXml(sessionScope.user.name)}!
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/projects/">Projects</a></li>
                                <li><a href="${pageContext.request.contextPath}/tasks/">Tasks</a></li>
                                <li><a href="${pageContext.request.contextPath}/entries/">Entries</a></li>
                                <li><a href="${pageContext.request.contextPath}/logout/">Sign Out</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading">Projects</div>
                <table class="table">
                    <tr>
                        <th style="width: 10%">#</th>
                        <th style="width: 50%">Customer</th>
                        <th style="width: 20%">Name</th>
                        <th style="width: 10%"></th>
                    </tr>

                    <c:choose>
                        <c:when test="${empty projectList}">
                            <tr>
                                <td colspan="5" style="text-align: center">There are no projects.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="project" items="${projectList}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${project.customer.name}</td>
                                    <td>${project.name}</td>
                                    <td>
                                        <c:if test="${not inEditMode}">
                                            <a href="/projects?edit=${project.id}" class="btn" role="button">
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
            <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

            <form action="#" method="post">
                <div class="panel panel-default">
                    <div class="panel-heading">${inEditMode ? "Edit" : "Create New"} Project</div>
                    <table class="table">
                        <tr>
                            <td>Customer:</td>
                            <td>
                                <select name="customer">
                                    <c:forEach items="${customerList}" var="customer">
                                        <option value="${customer.id}">${customer.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Name:</td>
                            <td><input type="test" name="name" value="${inEditMode ? editProject.name : ""}"></td>
                        </tr>
                        <c:if test="${inEditMode}">
                            <input type="hidden" name="projectId" value="${editProject.id}">
                        </c:if>
                    </table>
                </div>
                <button type="submit" class="btn btn-primary">
                    <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span>
                    Save Project
                </button>
            </form>
        </div>
    </body>
</html>
