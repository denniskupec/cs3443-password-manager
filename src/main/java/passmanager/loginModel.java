package passmanager;
import passmanager.database.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class loginModel {
    Connection connection;
    public loginModel()
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
    public boolean validatePassword(String user, String pass) throws Exception{
            PreparedStatement pr = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM user where username = ? and password = ?";
            pr = connection.prepareStatement(sql);
            pr.setString(1, user);
            pr.setString(2, pass);
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
        connection.close();
        return count;
    }
    public void addUser(String username, String password) throws Exception {
        String sql = "INSERT INTO user (username, password) VALUES ('"+username+"', '"+password+"')";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }
}
