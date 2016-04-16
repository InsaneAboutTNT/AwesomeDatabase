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