package passmanager.signuplogin;
import passmanager.database.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class databaseModel {
    public Connection connection;
    public databaseModel()
    {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(this.connection == null)
        {
            System.exit(1);
        }
    }
    public boolean isConnected() {
        return this.connection != null;
    }
    public boolean validatePassword(String pass) throws Exception{
            PreparedStatement pr = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM user where password = ?";
            pr = connection.prepareStatement(sql);
            pr.setString(1, pass);
            rs = pr.executeQuery();
            return rs.next();
    }
    public int count() throws Exception {
        int count = 0;
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM user";
        pr = connection.prepareStatement(sql);
        rs = pr.executeQuery();
        if (rs.next()) {
            count = rs.getInt("count(*)");
        }
        rs.close();
        pr.close();
        return count;
    }
    public void addUser(String password) throws Exception {
        String sql = "INSERT INTO user (password) VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, password);
        ps.executeUpdate();
        ps.close();
    }
}
