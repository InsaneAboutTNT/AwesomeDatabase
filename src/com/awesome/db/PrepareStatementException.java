package com.awesome.db;

/**
 * @author InsaneAboutTNT
 *
 */
public class PrepareStatementException extends RuntimeException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create a prepared statement exception
	 * @param query
	 */
	public PrepareStatementException(String query) {
		super("Prepared statement creation failed, query: " + query);
	}
	/**
	 * Create a prepared statement exception.
	 * @param query
	 */
	public PrepareStatementException(String query, Throwable e) {
		super("Prepared statement creation failed, query: " + query, e);
	}
}
