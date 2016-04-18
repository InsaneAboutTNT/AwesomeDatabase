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
	private Connection dbConnection;
	/**
	 * Construct a query
	 * @throws SQLException 
	 */
	public Query(Connection dbConnection, String query) {
		try {
			statement = dbConnection.prepareStatement(query);
			this.dbConnection = dbConnection;
		} catch (SQLException e) {
			throw new PrepareStatementException(query);
		}
	}
	/**
	 * Perform update with SQL script
	 * @throws SQLException 
	 */
	public void executeUpdate() throws SQLException {
		try {
			dbConnection.setAutoCommit(false);
			statement.executeUpdate();
			dbConnection.commit();
		} catch (SQLException e) {
			throw new QueryExecutionException("Query statement didn't execute successfully", e);
		} finally {
			if (statement != null) statement.close();
			if (dbConnection != null) {
				dbConnection.rollback(); // Roll it back
				dbConnection.close();
			}
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
			throw new QueryExecutionException("Query statement didn't execute successfully", e);
		} finally {
			if (statement != null) statement.close();
			if (dbConnection != null) dbConnection.close();
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
	public Query setParam(int index, String value) {
		
		try {
			statement.setString(index, value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	public Query setParam(int index, Object value) {
		setParam(index, value);
		return this;
	}
	/**
	 * Set a parameter in the PreparedStatement
	 * @param index The index in the statement
	 * @param value The value to put
	 * @return Query object itself
	 */
	public Query setParam(int index, double value) {
		return setParam(index, Double.toString(value));
	}
	/**
	 * @param index The index in the statement
	 * @param value The value to put
	 * @return Query object itself
	 */
	public Query setParam(int index, int value) {
		return setParam(index, Integer.toString(value));
	} // TODO add more

	/**
	 * Set a parameter in the PreparedStatement
	 * @param index The index in the statement
	 * @param value The value to put
	 * @return Query object itself
	 */
	public Query setParam(int index, long value) {
		return setParam(index, Long.toString(value));
	}
	
	/**
	 * Set a parameter in the PreparedStatement
	 * @param index The index in the statement
	 * @param value The value to put
	 * @return Query object itself
	 */
	public Query setParam(int index, float value) {
		return setParam(index, Float.toString(value));
	}
	
	/**
	 * Set a parameter in the PreparedStatement
	 * @param index The index in the statement
	 * @param value The value to put
	 * @return Query object itself
	 */
	public Query setParam(int index, boolean value) {
		return setParam(index, Boolean.toString(value));
	}
	/*
	 * End of setParam methods
	 * */
	/**
	 * Set parameters on the Query
	 * @param values The values to use as parameters
	 * @return Query itself
	 * @throws SQLException
	 */
	public Query params(Object... values) {
		ParameterMetaData meta = null;
		// See if we can actually get the
		// number of required params
		try {
			meta = statement.getParameterMetaData();
			if (meta != null) {
				int paramsCount = values == null ? 0 : values.length;
		        
                if (meta.getParameterCount() != paramsCount) {
                    throw new IncorrectNumberOfParamsException
                    		(paramsCount, meta.getParameterCount());
                }
			}
		} catch (SQLFeatureNotSupportedException e) {}
		catch (SQLException e) {}

		for (int i = 0; i < values.length; i++) {
			// Query parameters are indexed like 1, 2, 3..
			this.setParam(i+1, values[i]);
		}
		return this;
	}
}
