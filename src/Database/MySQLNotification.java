package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLNotification {

    private static final String URL = "jdbc:mysql://localhost:3305/bankoop_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found!");
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Notification DB connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Notification DB connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing notification DB connection");
        }
    }
}
