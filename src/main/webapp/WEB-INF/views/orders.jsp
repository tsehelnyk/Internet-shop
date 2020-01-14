<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.model.Order>"/>
<%--
  Created by IntelliJ IDEA.
  User: sagara
  Date: 12.01.2020
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Orders</title>
    </head>
    <body>
        User orders here!
        <table border="1">
            <tr>
                <th>Order ID</th>
                <th>User ID</th>
                <th>Price</th>
                <th>Delete from bucket</th>
            </tr>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>
                        <c:out value="${order.id}" />
                    </td>
                    <td>
                        <c:out value="${order.user}" />
                    </td>
                    <td>
                        <a href="/internet_shop_war_exploded/delOrder?order_id=${order.id}">DELETE</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <hr>
        <a href="/internet_shop_war_exploded/index">Main menu</a>
        <hr>
    </body>
</html>
