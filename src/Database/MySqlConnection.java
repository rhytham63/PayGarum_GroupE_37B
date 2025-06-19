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
        String user = "root";
        String password = "SushIL@#4413";
        String database = "bankoop_db";
        
        String url = "jdbc:mysql://127.0.0.1:3306/" + database;
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("Database connected successfully!");
        return conn;

    } catch (SQLException e) {
        System.err.println("Connection failed: " + e.getMessage());
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
 