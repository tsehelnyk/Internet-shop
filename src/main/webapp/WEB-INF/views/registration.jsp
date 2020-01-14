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
        <title>Registration</title>
    </head>
    <body>
        Register new user here!

        <form action="/internet_shop_war_exploded/registration" method="post">
            <div class="container">
                <h1>Register</h1>
                <p>Please fill in this form to create an account.</p>
                <hr>

                <label for="name"><b>Name</b></label>
                <input type="text" placeholder="Enter name" name="name" required>

                <label for="login"><b>Login</b></label>
                <input type="text" placeholder="Enter login" name="login" required>

                <label for="psw"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="psw" required>

                <label for="psw-repeat"><b>Repeat Password</b></label>
                <input type="password" placeholder="Repeat Password" name="psw-repeat" required>
                <hr>

                <button type="submit" class="registerbtn">Register</button>
            </div>

            <div class="container signin">
                <p>Already have an account? <a href="#">Sign in</a>.</p>
            </div>
        </form>
        <hr>
        <a href="/internet_shop_war_exploded/index">Main menu</a>
        <hr>
        <hr>
        <a href="/internet_shop_war_exploded/users">List of users</a>
        <hr>

    </body>
</html>
