package DAO;


import Database.MySqlConnection;
import Model.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DAO {
    private final MySqlConnection dbConnection;
    
    public DAO() {
        this.dbConnection = new MySqlConnection();
    }
    
    public boolean registerUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) return false;
            
            String query = "INSERT INTO users (full_name, email, password, date_of_birth) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword()); // In production, hash this!
            pstmt.setDate(4, new java.sql.Date(user.getDateOfBirth().getTime()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Registration failed");
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
            }
            dbConnection.closeConnection(conn);
        }
    }
    
    public void logIn(String username, String password) {
        System.out.println("logging...");
        MySqlConnection mySql = new MySqlConnection();
        Connection conn1 = mySql.openConnection();
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (PreparedStatement pstm = conn1.prepareStatement(sql)) {
            pstm.setString(1, username);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            System.out.println(sql+" "+username+" "+password);
            if (rs.next()) {
                // Login successful
                JOptionPane.showMessageDialog(null, "Login successful");
            } else {
                // Login failed
                JOptionPane.showMessageDialog(null, "Login failed: Invalid username or password");
            }

        } catch (SQLException ex) {
           Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occurred during login");
        } finally {
            mySql.closeConnection(conn1);
        }

    
}

    
}