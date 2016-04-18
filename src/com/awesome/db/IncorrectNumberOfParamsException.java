/**
 * 
 */
package com.awesome.db;

/**
 * @author InsaneAboutTNT
 *
 */
public class IncorrectNumberOfParamsException extends RuntimeException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param message
	 */
	public IncorrectNumberOfParamsException(int number, int expectedNumber) {
		super("Incorrect number of parameters :-( expected number: " + expectedNumber
				+ " input number: " + number);
		// TODO Auto-generated constructor stub
	}
}
