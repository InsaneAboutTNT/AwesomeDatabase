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
		super("Prepar statment creation fail, qeury: " + query);
	}
	/**
	 * Create a prepared statement exception.
	 * @param query
	 */
	public PrepareStatementException(String query, Throwable e) {
		super("Prepar statment creation fail, qeury: " + query, e);
	}
}
