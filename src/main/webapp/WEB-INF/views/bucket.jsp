<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>
<%--
  Created by IntelliJ IDEA.
  User: sagara
  Date: 12.01.2020
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Bucket</title>
    </head>
    <body>
        User's bucket content here!
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Delete from bucket</th>
            </tr>
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>
                        <c:out value="${item.id}" />
                    </td>
                    <td>
                        <c:out value="${item.name}" />
                    </td>
                    <td>
                        <c:out value="${item.price.toString()}" />
                    </td>
                    <td>
                        <a href="/internet_shop_war_exploded/delFromBucket?item_id=${item.id}">DELETE</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <hr>
        <a href="/internet_shop_war_exploded/newOrder">Complete order</a>
        <hr>
        <hr>
        <a href="/internet_shop_war_exploded/index">Main menu</a>
        <hr>
        <hr>
        <a href="/internet_shop_war_exploded/items">Catalogue</a>
        <hr>
    </body>
</html>
