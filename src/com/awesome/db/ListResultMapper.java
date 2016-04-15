/**
 * 
 */
package com.awesome.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Toshiba
 *
 */
public abstract class ListResultMapper<RowType> implements ResultMapper<List<RowType>> {

	/**
	 * 
	 */
	public ListResultMapper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<RowType> map(ResultSet resultSet) throws SQLException{
		List<RowType> list = new ArrayList<RowType>();
		while (resultSet.next()) {
			list.add(mapRow(resultSet));
		}
		return list;
	}
	public abstract RowType mapRow(ResultSet resultSet) throws SQLException;

}
