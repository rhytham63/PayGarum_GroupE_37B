package DAO;

import Database.MySqlConnection;
import Model.User;
import java.sql.*;

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
}