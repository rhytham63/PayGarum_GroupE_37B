package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class MySQLConnection implements database {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
        }
    }

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Connection openConnection() {
        try {
            String username = "root";
            String password = "SushIL@#4413";
            String database = "bank_db";
            
            // Fixed connection URL
            String url = "jdbc:mysql://localhost:3306/" + database;
            
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