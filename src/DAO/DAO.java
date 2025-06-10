package DAO;

import Database.MySqlConnection;
import Model.Session;
import Model.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class DAO {

    
    
    
    
    private  MySqlConnection dbConnection = new MySqlConnection();


    public boolean registerUser(User user) {
        Connection conn = dbConnection.openConnection();;
        PreparedStatement pstmt = null;

        try {
            
            if (conn == null) {
                return false;
            }

            String query = "INSERT INTO users (full_name, email, password, date_of_birth, balance) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setDate(4, new java.sql.Date(user.getDateOfBirth().getTime()));
            pstmt.setDouble(5, 0.0); // Initial balance = 0

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Registration failed: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                dbConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String logIn(String email, String password) {
        Connection conn = dbConnection.openConnection();
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("email");
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dbConnection.closeConnection(conn);
        }
        return null;
    }

    public double getBalance(String email) {
        Connection conn = dbConnection.openConnection();
        String query = "SELECT balance FROM users WHERE email = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection(conn);
        }
        return 0.0;
    }

    public boolean addMoney(String email, double amount) {
        Connection conn = dbConnection.openConnection();
        String query = "UPDATE users SET balance = balance + ? WHERE email = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, email);
            int updated = pstmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    public boolean transferMoney(String fromEmail, String toEmail, double amount,String password) {
        boolean isSuccessful = false;
        Connection conn = dbConnection.openConnection();
        String query = "{CALL transfer_funds(?, ?, ?)}";
        String q = "SELECT * FROM users WHERE email = ? and password = ?";
        
        try(PreparedStatement pstmt = conn.prepareStatement(q)){
            pstmt.setString(1, fromEmail);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
           
            if(rs.next()){
                try (PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, fromEmail);
                    stmt.setString(2, toEmail);
                    stmt.setBigDecimal(3, new java.math.BigDecimal(amount));

                    boolean hasResults = stmt.execute();

                    // If there is a result set with a message (from handler)
                    if (hasResults) {
                        ResultSet result = stmt.getResultSet();
                        if (result.next()) {
                            String errorMsg = result.getString("message");
                            JOptionPane.showMessageDialog(null, errorMsg, "Transfer Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        isSuccessful = true;
                       
                    }
                    
 
                } catch (SQLException e) {
                    System.err.println("Transaction failed: " + e.getMessage());
                }
            }else{
                JOptionPane.showMessageDialog(null, "Password didn't match");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

       
        return isSuccessful;
    }
}

