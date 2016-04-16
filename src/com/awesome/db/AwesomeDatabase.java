/**
 * 
 */
package com.awesome.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * The base class of the Awesome Database!
 * You can create SQL queries through this class.
 * 
 * It supports {@link DataSource} and {@link DriverManager}, though
 * DataSource is preferred (see the DataSource javadoc).
 * If you use DriverManager you need to create connections
 * yourself to pass in to createQuery.
 * 
 * @author InsaneAboutTNT
 * @see Query
 */
public class AwesomeDatabase {
	private DataSource dataSource;
	/**
	 * Construct and initialise database manager
	 * No-args constructor, use this if you are manually
	 * making connections with DriverManager.
	 */
	public AwesomeDatabase() {
		this(null);
	}
	/**
	 * Constructor which accepts a data source
	 * @param dataSource A {@link DataSource}.
	 */
	public AwesomeDatabase(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	/**
	 * Create a query to execute, accepting a connection
	 * if you are manually creating connections
	 * @param queryString the query string to make
	 * @return the <Query>
	 */
	public Query createQuery(Connection connection, String queryString) throws SQLException {
		return new Query(connection, queryString);
	}
	/**
	 * Create a query to execute
	 * @param queryString the query string to make
	 * @return the Query
	 */
	public Query createQuery(String queryString) throws SQLException {
		return new Query(dataSource.getConnection(), queryString);
	}
	/**
	 * Initialise the driver. This only
	 * needs to be used if you are
	 * using DriverManager.
	 * @param DRIVER
	 */
	public static void initDriver(String DRIVER) {
		try {
			Class.forName(DRIVER).newInstance();
			System.out.println("Connected to database");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
