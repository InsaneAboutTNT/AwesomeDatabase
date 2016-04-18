package com.awesome.db;

/**
 * @author InsaneAboutTNT
 *
 */
public class DataSourceGetConnectionException extends RuntimeException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create a prepared statement exception
	 * @param query
	 */
	public DataSourceGetConnectionException(String query) {
		super("Can't get da connection from DataSource, query: " + query);
	}
	/**
	 * Create a prepared statement exception.
	 * @param query
	 */
	public DataSourceGetConnectionException(String query, Throwable e) {
		super("Can't get da connection from DataSource, query: " + query, e);
	}
}
