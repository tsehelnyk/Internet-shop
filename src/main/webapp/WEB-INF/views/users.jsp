
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.model.User>"/>

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
                <th>Role</th>
                <th>Delete user</th>
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
                        <c:out value="${user.roles}" />
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
