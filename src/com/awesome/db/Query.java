/**
 * 
 */
package com.awesome.db;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

/**
 * 
 * This is a utility class for running
 * SQL queries and updates through JDBC.
 * It wraps {@link PreparedStatement}.
 * 
 * You don't need to manually call <code>new Query();</code>,
 * rather you create it through an AwesomeDatabase instance with 
 * <code>awesomeDB.createQuery(sqlQuery)</code>.
 * @author InsaneAboutTNT
 */
public class Query {
	private PreparedStatement statement;
	/**
	 * Construct a query
	 * @throws SQLException 
	 */
	public Query(Connection dbConnection, String query) throws SQLException {
		statement = dbConnection.prepareStatement(query);
	}
	/**
	 * Perform update with SQL script
	 * @throws SQLException 
	 */
	public void executeUpdate() throws SQLException {
		try {
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Query statement didn't execute successfully");
		} finally {
			if (statement != null) statement.close();
		}
	}
	/**
	 * Execute a query, given a ResultMapper.
	 * Returns the Type object.
	 * @param mapper
	 * @return the Type
	 * @throws SQLException
	 */
	public <Type> Type executeQuery(ResultMapper<Type> mapper) throws SQLException {
		// TODO
		Type result;
		try {
			ResultSet resultSet = statement.executeQuery();
			result = mapper.map(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Query statement didn't execute successfully");
		} finally {
			if (statement != null) statement.close();
		}
		return result;
	}
	/*
	 * Below are the setParam methods
	 * each one accepts a different value type.
	 * */
	/**
	 * Set a parameter in the PreparedStatement
	 * @param index The index in the statement
	 * @param value The value to put
	 * @return Query object itself
	 */
	public Query setParam(int index, String value) throws SQLException {
		
		statement.setString(index, value);
		return this;
	}
	/**
	 * Set a parameter in the PreparedStatement
	 * @param index The index in the statement
	 * @param value The value to put
	 * @return Query object itself
	 * @throws SQLException 
	 */
	public Query setParam(int index, double value) throws SQLException {
		return setParam(index, Double.toString(value));
	}
	/**
	 * Set parameters on the Query
	 * @param values The values to use as parameters
	 * @return Query itself
	 * @throws SQLException
	 */
	public Query params(Object... values) throws SQLException {
		ParameterMetaData meta = null;
		// See if we can actually get the
		// number of required params
		try {
			meta = statement.getParameterMetaData();
			if (meta != null) {
				int paramsCount = values == null ? 0 : values.length;
		        
                if (meta.getParameterCount() != paramsCount) {
                    throw new SQLException("Wrong number of params given :-(");
                }
			}
		} catch (SQLFeatureNotSupportedException e) {}

		for (int i = 0; i < values.length; i++) {
			// Query parameters are indexed like 1, 2, 3..
			statement.setObject(i + 1, values[i]);
		}
		return this;
	}
	/**
	 * @param index The index in the statement
	 * @param value The value to put
	 * @return Query object itself
	 * @throws SQLException
	 */
	public Query setParam(int index, int value) throws SQLException {
		return setParam(index, Integer.toString(value));
	} // TODO add more
	/*
	 * Close the Query
	 * Maybe not needed
	 */
	public void close() {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Didn't close statement successfully");
			}
		}
	}
}