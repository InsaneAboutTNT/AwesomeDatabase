#Awesome Database!!
An awesome library for connecting an awesome database to the awesome JDBC API.

##Usage
Use it like (this is for Apache DB `EmbeddedDataSource`)
```
// Just some data source setup
EmbeddedDataSource dataSource = new EmbeddedDataSource();
dataSource.setUser("awesome");
dataSource.setPassword("awesome");
dataSource.setDatabaseName("MyDB");

// Create the instance
AwesomeDatabase awesomeDB = new AwesomeDatabase(dataSource);
try {
    // With a DataSource, the awesomeDatabase will automatically handle connections
	List<Item> results = awesomeDB.createQuery("CREATE TABLE awesometable")
			.executeUpdate();
} catch (SQLException e) {
	e.printStackTrace();
}
```