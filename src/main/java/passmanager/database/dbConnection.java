package passmanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    public static final String sqConnection = "jdbc:sqlite:src/main/java/passmanager/database/database.db";
    public static Connection getConnection() throws SQLException
    {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(sqConnection);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
