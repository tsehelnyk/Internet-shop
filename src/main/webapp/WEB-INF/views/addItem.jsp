
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
         <title>Add new item</title>
    </head>
    <body>
        Add new item here!

        <form action="/internet_shop_war_exploded/servlet/addItem" method="post">
            <div class="container">
                <h1>Add item</h1>
                <p>Please fill in this form to create an Item.</p>
                <hr>

                <label for="name"><b>Name</b></label>
                <input type="text" placeholder="Enter item name" name="name" required>

                <label for="price"><b>Price</b></label>
                <input type="text" placeholder="Enter item price" name="price" required>

                <button type="submit" class="addbtn">Add</button>
            </div>

        </form>
        <hr>
        <a href="/internet_shop_war_exploded/servlet/index">Main menu</a>
        <hr>
        <hr>
        <a href="/internet_shop_war_exploded/servlet/items">Catalogue</a>
        <hr>
    </body>
</html>
