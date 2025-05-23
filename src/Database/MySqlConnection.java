package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.*;

public class MySqlConnection implements Database {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
        }
    }

    @Override
    public Connection openConnection() {
        try {
            String username = "root";
            String password = "1234";
            String database = "customers_login";
            
            // Fixed connection URL
            String url = "jdbc:mysql://localhost:3305/" + database;
            
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            return null;
        }
    }

    @Override
    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection");
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Query execution failed");
            return null;
        }
    }

    @Override
    public int executeUpdate(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Update execution failed");
            return -1;
        }
    }
}