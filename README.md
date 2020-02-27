Sample application to demonstrate the difference in performance between mssql jdbc and jtds.
Just enable the dependency of the database driver in the **build.sbt** file and the **conf/application.conf**

sbt and a sql server are required.

To start the application call the **sbt** command within the project folder and then execute the **run** command 
the application is available under [localhost:9000](http://localhost:9000)

The query to test the performance is executed when **HomeController#testSelect()** is called
```java
Map<Long, Customer> customers = InjectorFactory.db().find(Customer.class).fetch("customerGroups").setMapKey("id").findMap();
```

Executed queries are logged in the **log/sql.log**
