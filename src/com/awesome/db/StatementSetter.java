package com.awesome.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author InsaneAboutTNT
 *
 */
public interface StatementSetter {
	public void prepareStatement(PreparedStatement statement) throws SQLException;
}
