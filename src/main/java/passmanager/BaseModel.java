/**
 * 
 */
package passmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class BaseModel {
	
	protected String tableName = null;

	/**
	 * A generic select method. Not type safe and is probably a bad idea.
	 * Works for this use case. 
	 * @param columnName	name of the column to fetch
	 * @return T - Type depends on the database column type
	 */
	@SuppressWarnings("unchecked")
	protected final <T> T select(String columnName) {
		tableName = (tableName == null) ? getClass().getSimpleName().toLowerCase() : tableName;
		
		try (Connection db = Database.connect()) {
			ResultSet rs = db.createStatement().executeQuery("SELECT " + columnName + " FROM " + tableName);
			return (T) rs.getObject(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * A generic update method. Also probably a bad idea, but types are checked.
	 * @param columnName - name of the database column that needs updating
	 * @param value - new value for that column, type depends on the database column type
	 * @return boolean - True on success, false on failure
	 */
	protected final <T> boolean update(String columnName, T value) {
		tableName = (tableName == null) ? getClass().getSimpleName().toLowerCase() : tableName;
		
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("UPDATE " + tableName + " SET " + columnName + "=?")) {
				stmt.setObject(1, value);
				
				return stmt.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
}
