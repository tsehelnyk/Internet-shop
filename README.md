# Internet-shop

# My InternetShop project




# Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developer](#developer-start)
* [Author](#author)

# <a name="purpose"></a>Project purpose
This is a template for creating an e-store.
<hr>
It has registration and login tools for users authentication and authorization.

There are controller for all visitors - displaying all items in the store.

Also specific controllers only for registered users: 
 - for adding items to user`s bucket;
 - for completing order;
 - for displaying a lists of selected items in user`s bucket or orders;

 And specific controllers only for admins:
  - for adding items to the store;
  - for displaying a list of all users.
<hr>

# <a name="structure"></a>Project Structure
* Java 11
* Maven 3.8.0
* maven-checkstyle-plugin
* javax.servlet-api 3.1.0
* jstl 1.2
* mysql-connector-java 8.0.18
* log4j 1.2.17
<hr>

# <a name="developer-start"></a>For developer

Open the project in your IDE.

Add it as maven project.

Configure Tomcat:

add artifact;
add sdk 11.0.3
Add sdk 11.0.3 in project struсture.

Create a schema "storage" in MySQLWorkbench database.

Use my_interntet_shop.src.main.resources.init_db.sql to create all the tables required by this app.

At my_interntet_shop.src.main.java.mate.academy.internet.shop.factories.Factory class use your username 
and password for your DB to create a Connection.

Change a path in my_interntet_shop.src.main.resources.log4j.properties. It has to reach your logFile.

Run the project.

There’s one user with a Admin role (email = "admin", password = "1").

# <a name="author"></a>Author

https://github.com/tsehelnyk


