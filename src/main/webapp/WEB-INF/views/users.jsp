<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.model.User>"/>
<%--
  Created by IntelliJ IDEA.
  User: sagara
  Date: 12.01.2020
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Users</title>
    </head>
    <body>
        List of all Users
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>
                        <c:out value="${user.id}" />
                    </td>
                    <td>
                        <c:out value="${user.name}" />
                    </td>
                    <td>
                        <a href="/internet_shop_war_exploded/servlet/deleteUser?user_id=${user.id}">DELETE</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <hr>
        <a href="/internet_shop_war_exploded/servlet/index">Main menu</a>
        <hr>
        <hr>
        <a href="/internet_shop_war_exploded/registration">Registration</a>
        <hr>
    </body>
</html>
