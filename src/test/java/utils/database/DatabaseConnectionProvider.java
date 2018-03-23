package utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionProvider {
    public Connection getConnectionToDatabase(String host, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection(host, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
