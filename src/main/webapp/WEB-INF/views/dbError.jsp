
<jsp:useBean id="dpe_msg" scope="request" type="java.lang.String"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Error page</title>
    </head>
    <body>
        <h1>Something was wrong:</h1>
        <h3>${dpe_msg}</h3><br>
        <hr>
        <a href="/internet_shop_war_exploded/servlet/index">Main menu</a>
        <hr>
    </body>
</html>
