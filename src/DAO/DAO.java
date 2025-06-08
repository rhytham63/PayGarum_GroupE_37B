package DAO;

import Database.MySqlConnection;
import Model.Session;
import Model.User;
import java.sql.*;

public class DAO {
    private final MySqlConnection dbConnection;

    public DAO() {
        dbConnection = new MySqlConnection();
    }

    public boolean registerUser(User user) {
        Connection conn = dbConnection.openConnection();
        if (conn == null) {
            return false;
        }

        String query = "INSERT INTO users (full_name, email, password, date_of_birth, balance) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setDate(4, new java.sql.Date(user.getDateOfBirth().getTime()));
            stmt.setDouble(5, 0.0);

            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    public boolean logIn(String email, String password) {
        Connection conn = dbConnection.openConnection();
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Session.loggedInUserEmail = email;
                stmt.close();
                return true;
            }
            stmt.close();
            return false;
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    public double getBalance(String email) {
        Connection conn = dbConnection.openConnection();
        String query = "SELECT balance FROM users WHERE email = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                double balance = result.getDouble("balance");
                stmt.close();
                return balance;
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error getting balance: " + e.getMessage());
        } finally {
            dbConnection.closeConnection(conn);
        }
        return 0.0;
    }

    public boolean addMoney(String email, double amount) {
        Connection conn = dbConnection.openConnection();
        String query = "UPDATE users SET balance = balance + ? WHERE email = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, amount);
            stmt.setString(2, email);
            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding money: " + e.getMessage());
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }
    
    public boolean hasBookedEvent(String email, String eventName) {
    Connection conn = dbConnection.openConnection();
    String query = "SELECT * FROM event_bookings WHERE user_email = ? AND event_name = ?";

    try {
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, eventName);
        ResultSet rs = stmt.executeQuery();
        boolean booked = rs.next();
        stmt.close();
        return booked;
    } catch (SQLException e) {
        System.out.println("Error checking event booking: " + e.getMessage());
        return false;
    } finally {
        dbConnection.closeConnection(conn);
    }
}

public boolean bookEvent(String email, String eventName, double cost) {
    Connection conn = dbConnection.openConnection();

    try {
        conn.setAutoCommit(false);  // transaction

        // check balance
        String checkQuery = "SELECT balance FROM users WHERE email = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, email);
        ResultSet rs = checkStmt.executeQuery();

        if (!rs.next() || rs.getDouble("balance") < cost) {
            conn.rollback();
            return false;
        }

        // deduct money
        String deductQuery = "UPDATE users SET balance = balance - ? WHERE email = ?";
        PreparedStatement deductStmt = conn.prepareStatement(deductQuery);
        deductStmt.setDouble(1, cost);
        deductStmt.setString(2, email);
        deductStmt.executeUpdate();

        // insert booking
        String insertQuery = "INSERT INTO event_bookings (user_email, event_name) VALUES (?, ?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
        insertStmt.setString(1, email);
        insertStmt.setString(2, eventName);
        insertStmt.executeUpdate();

        conn.commit();
        return true;

    } catch (SQLException e) {
        try { conn.rollback(); } catch (SQLException ignore) {}
        System.out.println("Booking failed: " + e.getMessage());
        return false;
    } finally {
        dbConnection.closeConnection(conn);
    }
}

}