/**
 * 
 */
package com.awesome.db;

/**
 * @author InsaneAboutTNT
 *
 */
public class QueryExecutionException extends RuntimeException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public QueryExecutionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public QueryExecutionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
