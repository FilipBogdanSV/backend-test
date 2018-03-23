package utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperations {
    private Statement statement;

    private DatabaseOperations(Connection databaseConnection, String databaseName) {
        try {
            statement = databaseConnection.createStatement();
            statement.executeQuery("use `" + databaseName + '`');
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseOperations getDatabaseService() {
        return new DatabaseOperations(new DatabaseConnectionProvider()
                .getConnectionToDatabase("jdbc:mysql://85.204.241.125:3306", "sinfl-filip-bogdan", "98rbmn"), "sinfl-filip-bogdan");
    }

    public static void createDatabase(String databaseName, Connection connection) throws SQLException {
        Statement statementToCreateDatabase = connection.createStatement();
        statementToCreateDatabase.executeQuery("CREATE DATABASE " + databaseName);
    }

    public void createTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE Users (\n" +
                "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n" +
                "username VARCHAR(30) NOT NULL,\n" +
                "password VARCHAR(30) NOT NULL,\n" +
                "email VARCHAR(50),\n" +
                "reg_date TIMESTAMP\n" +
                ")");
    }

    public ResultSet queryUsers(String tableName) throws SQLException {
        return statement.executeQuery("select * from " + tableName);
    }

    public void addNewRegistration(String username, String password, String email, String tableName) throws SQLException {
        statement.executeUpdate(String.format("INSERT INTO " + tableName+ "(username, password, email)"+ " values ('%s', '%s', '%s')", username, password, email));
    }
}
