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
                                <li><a href="${pageContext.request.contextPath}/customers/">Customers</a></li>
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
                <div class="panel-heading">Customers</div>
                <table class="table">
                    <tr>
                        <th style="width: 5%">#</th>
                        <th>Name</th>
                        <th>Address 1</th>
                        <th>Address 2</th>
                        <th>Address 3</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Zip</th>
                        <th>Phone 1</th>
                        <th>Phone 2</th>
                        <th>Fax 1</th>
                        <th>Fax 2</th>
                        <th>Email</th>
                        <th>Website</th>
                        <th></th>
                    </tr>

                    <c:choose>
                        <c:when test="${empty customerList}">
                            <tr>
                                <td colspan="5" style="text-align: center">There are no customers.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="customer" items="${customerList}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${customer.name}</td>
                                    <td>${customer.address1}</td>
                                    <td>${customer.address2}</td>
                                    <td>${customer.address3}</td>
                                    <td>${customer.city}</td>
                                    <td>${customer.state}</td>
                                    <td>${customer.zip}</td>
                                    <td>${customer.phone1}</td>
                                    <td>${customer.phone2}</td>
                                    <td>${customer.fax1}</td>
                                    <td>${customer.fax2}</td>
                                    <td>${customer.email}</td>
                                    <td>${customer.website}</td>
                                    <td>
                                        <c:if test="${not inEditMode}">
                                            <a href="/customers?edit=${customer.id}" class="btn" role="button">
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
                    <div class="panel-heading">${inEditMode ? "Edit" : "Create New"} Customer</div>
                    <table class="table">
                        <tr>
                            <td>Name:</td>
                            <td><input type="text" name="name" value="${inEditMode ? editCustomer.name : ""}"></td>
                        </tr>
                        <tr>
                            <td>Address 1:</td>
                            <td><input type="text" name="address1" value="${inEditMode ? editCustomer.address1 : ""}"></td>
                        </tr>
                        <tr>
                            <td>Address 2:</td>
                            <td><input type="text" name="address2" value="${inEditMode ? editCustomer.address2 : ""}"></td>
                        </tr>
                        <tr>
                            <td>Address 3:</td>
                            <td><input type="text" name="address3" value="${inEditMode ? editCustomer.address3 : ""}"></td>
                        </tr>
                        <tr>
                            <td>City:</td>
                            <td><input type="text" name="city" value="${inEditMode ? editCustomer.city : ""}"></td>
                        </tr>
                        <tr>
                            <td>State:</td>
                            <td><input type="text" name="state" value="${inEditMode ? editCustomer.state : ""}"></td>
                        </tr>
                        <tr>
                            <td>Zip:</td>
                            <td><input type="text" name="zip" value="${inEditMode ? editCustomer.zip : ""}"></td>
                        </tr>
                        <tr>
                            <td>Phone 1:</td>
                            <td><input type="text" name="phone1" value="${inEditMode ? editCustomer.phone1 : ""}"></td>
                        </tr>
                        <tr>
                            <td>Phone 2:</td>
                            <td><input type="text" name="phone2" value="${inEditMode ? editCustomer.phone2 : ""}"></td>
                        </tr>
                        <tr>
                            <td>Fax 1:</td>
                            <td><input type="text" name="fax1" value="${inEditMode ? editCustomer.fax1 : ""}"></td>
                        </tr>
                        <tr>
                            <td>Fax 2:</td>
                            <td><input type="text" name="fax2" value="${inEditMode ? editCustomer.fax2 : ""}"></td>
                        </tr>
                        <tr>
                            <td>Email:</td>
                            <td><input type="text" name="email" value="${inEditMode ? editCustomer.email : ""}"></td>
                        </tr>
                        <tr>
                            <td>Website:</td>
                            <td><input type="text" name="website" value="${inEditMode ? editCustomer.website : ""}"></td>
                        </tr>
                        <c:if test="${inEditMode}">
                            <input type="hidden" name="customerId" value="${editCustomer.id}">
                        </c:if>
                    </table>
                </div>
                <button type="submit" class="btn btn-primary">
                    <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span>
                    Save Customer
                </button>
            </form>
        </div>
    </body>
</html>
