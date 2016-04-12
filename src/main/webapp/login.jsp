<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.domain.user.UserService" %>
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
        <div class="container">
            <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

            <form action="#" method="post">
                <div class="panel panel-default">
                    <table class="table">
                        <tr>
                            <td>Username:</td>
                            <td><input type="text" name="username"/></td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><input type="password" name="password"/></td>
                        </tr>
                    </table>
                </div>
                <button type="submit" class="btn btn-primary">Log In</button>
                <a type="button" class="btn btn-default" href="/register">Register</a>
            </form>
        </div>
    </body>
</html>
