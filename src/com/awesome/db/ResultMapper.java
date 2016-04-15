/**
 * 
 */
package com.awesome.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author InsaneAboutTNT
 *
 */
public interface ResultMapper<Type> {

	/**
	 * @throws SQLException 
	 * 
	 */
	public Type map(ResultSet resultSet) throws SQLException;

}
