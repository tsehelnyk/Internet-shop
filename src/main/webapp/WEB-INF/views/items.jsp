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
        <title>Items</title>
    </head>
    <body>
        All items Here!
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Add to Bucket</th>
                <th>Delete from catalogue</th>
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
                        <a href="/internet_shop_war_exploded/servlet/add2bucket?item_id=${item.id}">ADD</a>
                    </td>
                    <td>
                        <a href="/internet_shop_war_exploded/servlet/deleteItem?item_id=${item.id}">DELETE</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <hr>
        <a href="/internet_shop_war_exploded/servlet/addItem">Add item to catalogue</a>
        <hr>
        <hr>
        <a href="/internet_shop_war_exploded/servlet/index">Main menu</a>
        <hr>
        <hr>
        <a href="/internet_shop_war_exploded/servlet/bucket">User's bucket</a>
        <hr>
    </body>
</html>
