
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.model.Order>"/>

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
                <th>Delete order</th>
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
                        <a href="/internet_shop_war_exploded/servlet/delOrder?order_id=${order.id}">DELETE</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <hr>
        <a href="/internet_shop_war_exploded/servlet/index">Main menu</a>
        <hr>
    </body>
</html>
