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
import java.util.HashMap;
import java.util.Map;

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
	private Connection dbConnection;
	private Map<Integer, Object> paramMap = new HashMap<>();
	private String query;
	/**
	 * Construct a query
	 * @throws SQLException 
	 */
	public Query(Connection dbConnection, String query) {
		this.query = query;
		this.dbConnection = dbConnection;
	}
	private PreparedStatement prepareFromDataSource() {
		PreparedStatement statement;
		try {
			// Only create statement on execution
			statement = dbConnection.prepareStatement(query);
			ParameterMetaData meta = null;
			// See if we can actually get the
			// number of required params
			try {
				meta = statement.getParameterMetaData();
				if (meta != null) {
					int paramsCount = paramMap.size();
			        
	                if (meta.getParameterCount() != paramsCount) {
	                    throw new IncorrectNumberOfParamsException
	                    		(paramsCount, meta.getParameterCount());
	                }
				}
			} catch (SQLFeatureNotSupportedException e) {}
			catch (SQLException e) {}
			// Add parameters to statement
			for (Map.Entry<Integer, Object> paramEntry: paramMap.entrySet()) {
				statement.setObject(paramEntry.getKey(), paramEntry.getValue());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new PrepareStatementException(query, e);
		}
		return statement;
	}
	/**
	 * Perform update with SQL script
	 * @throws SQLException 
	 */
	public void executeUpdate() {
		try (PreparedStatement statement = prepareFromDataSource()){
			dbConnection.setAutoCommit(false);
			statement.executeUpdate();
			dbConnection.commit();
		} catch (SQLException e) {
			throw new QueryExecutionException("Query statement didn't execute successfully", e);
		} finally {
			//dbConnection.rollback(); // Roll it back
		}
	}
	/**
	 * Execute a query, given a ResultMapper.
	 * Returns the Type object.
	 * @param mapper
	 * @return the Type
	 * @throws SQLException
	 */
	public <Type> Type executeQuery(ResultMapper<Type> mapper) {
		// TODO
		Type result;
		try (PreparedStatement statement = prepareFromDataSource()){
			ResultSet resultSet = statement.executeQuery();
			result = mapper.map(resultSet);
		} catch (SQLException e) {
			throw new QueryExecutionException("Query statement didn't execute successfully", e);
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
	public Query setParam(int index, Object value) {
		
		paramMap.put(index, value);
		return this;
	}
	public Query setParam(int index, String value) {
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

		for (int i = 0; i < values.length; i++) {
			// Query parameters are indexed like 1, 2, 3..
			this.setParam(i+1, values[i]);
		}
		return this;
	}
}
