# Internet-shop


# Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developer](#developer-start)
* [Author](#author)

# <a name="purpose"></a>Project purpose
This is a template for creating an e-store.

Everyone can see all available items in the store. Users can register and then log-in for getting possibilities to add items to their personal bucket and make orders.

There are specific tools for admins only to allow them adding items to the store and viewing a list of all registered users.

<hr>

# <a name="structure"></a>Project Structure
* Java 11
* Maven 3.8.0
* JUnit 4.12
* Maven-checkstyle-plugin
* javax.servlet-api 3.1.0
* JSTL 1.2
* mysql-connector-java 8.0.18
* log4j 1.2.17
<hr>

# <a name="developer-start"></a>For developer

1. Open the project in your IDE.

2. Add it as maven project.

3. Configure Tomcat:
add artifact;
add sdk 11.0.3
Add sdk 11.0.3 in project struсture.

4. Create a schema "storage" in MySQLWorkbench database. Default schema's name "test".

5. Use Interntet-shop.src.main.resources.init_db.sql to create all the tables required by this app.

6. At Interntet-shop.src.main.java.mate.academy.internet.shop.factories.Factory class use your username 
and password for your DB to create a Connection.

7. You should to configure a path of logs in Interntet-shop.src.main.resources.log4j.properties.

8. Run the project.

9. Register users. Default user role - "USER".

10. To add items and manage users you should change role for your admin-user to "ADMIN" in database. 
<hr>

# <a name="author"></a>Author

https://github.com/tsehelnyk


