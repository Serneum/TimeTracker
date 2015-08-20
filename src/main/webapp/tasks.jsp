<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    </head>

    <body>

    <%
        String guestbookName = request.getParameter("guestbookName");
        if (StringUtils.isBlank(guestbookName)) {
            guestbookName = "default";
        }
        pageContext.setAttribute("guestbookName", guestbookName);
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
            pageContext.setAttribute("user", user);
        }
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

    <form action="/guestbook.jsp" method="get">
        <div><input type="text" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/></div>
        <div><input type="submit" value="Switch Guestbook"/></div>
    </form>

    </body>
</html>
