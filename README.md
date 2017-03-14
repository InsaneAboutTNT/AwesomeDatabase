*Not recommended for any actual database access*

#Awesome Database!!
An awesome library for connecting an awesome database to the awesome JDBC API.

##Usage
To perform an update (this is for Apache DB `EmbeddedDataSource`)
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
	awesomeDB.createQuery("CREATE TABLE awesometable")
			.executeUpdate();
} catch (SQLException e) {
	e.printStackTrace();
}
```

You can also query from the database.
```
awesomeDB.createQuery("SELECT * FROM products")
	.executeQuery(new ListResultMapper<Item>() {
		public Item mapRow(ResultSet rs) throws SQLException {
			Item item = new Item();
			item.name = rs.getString("Name");
			item.brand = rs.getString("Brand");
			item.price = rs.getDouble("Price");
			return item;
		}
	});
```

##ResultMapper Interface
Implement `ResultMapper` or any of the abstract classes implementing this to map from a ResultSet to an object when querying in a database.

For example: (For `ListResultMapper`)

```
ResultMapper<Item> mapper = new ListResultMapper<Item>() {
	// mapRow converts a single row to an object and returns it
	public Item mapRow(ResultSet rs) throws SQLException {
		Item item = new Item();
		item.name = rs.getString("Name");
		item.brand = rs.getString("Brand");
		item.price = rs.getDouble("Price");
		return item;
	}
}
```
The `ResultMapper` has an abstract method `map(ResultSet resultSet)` to convert the result set to an object and return it. (For `ListResultMapper` implement the method `mapRow` as shown above.) In the mapping, you can retrieve the values from the ResultSet with `getString()`, `getDouble()`, `getObject()` etc (the ResultSet is already built in Java). Check the ResultSet javadoc [here](https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html).

After creating it, use it with:
```
awesomeDB.createQuery("SELECT * FROM products") // or put your own query
	.executeQuery(mapper);
```
