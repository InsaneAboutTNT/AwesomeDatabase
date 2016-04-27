/**
 * 
 */
package com.awesome.db;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private String query;
	private StatementSetter statementSetter;
	/**
	 * Construct a query
	 * @throws SQLException 
	 */
	public Query(Connection dbConnection, String query) {
		this.query = query;
		this.dbConnection = dbConnection;
	}
	private PreparedStatement createPreparedStatement() {
		PreparedStatement statement;
		try {
			statement = dbConnection.prepareStatement(query);
			// Add parameters to statement
			if (statementSetter != null) statementSetter.prepareStatement(statement);
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
		try (PreparedStatement statement = createPreparedStatement()){
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new QueryExecutionException("Query statement didn't execute successfully", e);
		} finally {
			//dbConnection.rollback(); // Roll it back
		}
	}
	/**
	 * Execute a batched query. Internally, it
	 * runs {@link java.sql.PreparedStatement#executeBatch()}
	 */
	public void executeBatch() {
		try (PreparedStatement statement = createPreparedStatement()){
			dbConnection.setAutoCommit(false);
			statement.executeBatch();
			dbConnection.commit();
			dbConnection.setAutoCommit(true);
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
		try (PreparedStatement statement = createPreparedStatement()){
			ResultSet resultSet = statement.executeQuery();
			result = mapper.map(resultSet);
		} catch (SQLException e) {
			throw new QueryExecutionException("Query statement didn't execute successfully", e);
		} 
		return result;
	}
	/**
	 * Sets the {@link StatementSetter}, the class to be run
	 * when executeQuery() or executeBatch() is run.
	 * @param statementSetter
	 * @return Query
	 */
	public Query setStatement(StatementSetter statementSetter) {
		this.statementSetter = statementSetter;
		return this;
	}

	/**
	 * Set parameters on the Query
	 * @param values The values to use as parameters
	 * @return Query itself
	 * @throws SQLException
	 */
	public Query params(Object... values) {
		this.statementSetter = new StatementSetter() {
			public void prepareStatement(PreparedStatement statement) throws SQLException {
				ParameterMetaData pmd = statement.getParameterMetaData();
				try {
					int count = pmd.getParameterCount();
					if (values.length != count) {
						throw new IncorrectNumberOfParamsException(values.length, count);
					}
				} catch (SQLException e) {}

				for (int i = 0; i < values.length; i ++) {
					statement.setObject(i + 1, values[i]);
				}
			}
		};
		return this;
	}
}
