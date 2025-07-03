package DAO;

import Database.MySqlConnection;
import Model.Session;
import Model.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DAO {

    private MySqlConnection dbConnection;

    public DAO() {
        dbConnection = new MySqlConnection();
    }

    // Register user with security question/answer
    public boolean registerUser(User user, String securityQuestion, String securityAnswer) {
        Connection conn = dbConnection.openConnection();
        PreparedStatement pstmt = null;

        String query = "INSERT INTO users (full_name, email, password, date_of_birth, balance, gender, account_type, security_question, security_answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            if (conn == null) {
                return false;
            }

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setDate(4, new java.sql.Date(user.getDateOfBirth().getTime()));
            pstmt.setDouble(5, 0.0);
            pstmt.setString(6, user.getGender());
            pstmt.setString(7, user.getAccountType());
            pstmt.setString(8, securityQuestion);
            pstmt.setString(9, securityAnswer);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
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

    // Get security question for a user
    public String getSecurityQuestion(String email) {
        Connection conn = dbConnection.openConnection();
        String query = "SELECT security_question FROM users WHERE email = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("security_question");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection(conn);
        }
        return null;
    }

    // Check security answer for forgot password
    public boolean checkSecurityAnswer(String email, String answer) {
        Connection conn = dbConnection.openConnection();
        String query = "SELECT * FROM users WHERE email = ? AND security_answer = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, answer);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    // Update password after security answer is verified
    public boolean updatePassword(String email, String newPassword) {
        Connection conn = dbConnection.openConnection();
        String query = "UPDATE users SET password = ? WHERE email = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    public String logIn(String email, String password) {
        Connection conn = dbConnection.openConnection();
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Session.loggedInUserEmail = email;
                return email;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection(conn);
        }
        return null;
    }

    public double getBalance(String email) {
        Connection conn = dbConnection.openConnection();
        String query = "SELECT balance FROM users WHERE email = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return result.getDouble("balance");
            }
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
            return stmt.executeUpdate() > 0;
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
            return stmt.executeQuery().next();
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
            conn.setAutoCommit(false);

            String checkQuery = "SELECT balance FROM users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next() || rs.getDouble("balance") < cost) {
                conn.rollback();
                return false;
            }

            String deductQuery = "UPDATE users SET balance = balance - ? WHERE email = ?";
            PreparedStatement deductStmt = conn.prepareStatement(deductQuery);
            deductStmt.setDouble(1, cost);
            deductStmt.setString(2, email);
            deductStmt.executeUpdate();

            String insertQuery = "INSERT INTO event_bookings (user_email, event_name) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, email);
            insertStmt.setString(2, eventName);
            insertStmt.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ignore) {
            }
            System.out.println("Booking failed: " + e.getMessage());
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    public boolean transferMoney(String fromEmail, String toEmail, double amount, String password) {
        Connection conn = dbConnection.openConnection();
        boolean isSuccessful = false;

        try {
            String verifyQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement verifyStmt = conn.prepareStatement(verifyQuery);
            verifyStmt.setString(1, fromEmail);
            verifyStmt.setString(2, password);
            ResultSet rs = verifyStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Password didn't match");
                return false;
            }

            String transferProc = "{CALL transfer_funds(?, ?, ?)}";
            PreparedStatement stmt = conn.prepareStatement(transferProc);
            stmt.setString(1, fromEmail);
            stmt.setString(2, toEmail);
            stmt.setBigDecimal(3, new java.math.BigDecimal(amount));

            boolean hasResults = stmt.execute();

            if (hasResults) {
                ResultSet result = stmt.getResultSet();
                if (result.next()) {
                    String errorMsg = result.getString("message");
                    JOptionPane.showMessageDialog(null, errorMsg, "Transfer Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                isSuccessful = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isSuccessful;
    }

    // Retrieve user profile by email (assuming email is unique)
    public User getUserProfile(String email) throws SQLException {
        User user = null;
        String query = "SELECT full_name, email, date_of_birth, gender FROM users WHERE email = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setDateOfBirth(rs.getDate("date_of_birth"));
                user.setgender(rs.getString("gender"));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving user profile: " + e.getMessage(), e);
        }
        
        return user;
    }

    public boolean resetPassword(String email, String oldPass, String newPass) {
        Connection conn = dbConnection.openConnection();
        String checkQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
        String updateQuery = "UPDATE users SET password = ? WHERE email = ?";

        try {
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            checkStmt.setString(2, oldPass);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, newPass);
                updateStmt.setString(2, email);
                return updateStmt.executeUpdate() > 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    public boolean deleteUserAccount(String email) {
        Connection conn = dbConnection.openConnection();
        try {
            conn.setAutoCommit(false);

            // Delete related event bookings
            PreparedStatement deleteBookings = conn.prepareStatement(
                "DELETE FROM event_bookings WHERE user_email = ?"
            );
            deleteBookings.setString(1, email);
            deleteBookings.executeUpdate();

            // Delete user record
            PreparedStatement deleteUser = conn.prepareStatement(
                "DELETE FROM users WHERE email = ?"
            );
            deleteUser.setString(1, email);
            int rowsAffected = deleteUser.executeUpdate();

            conn.commit();
            return rowsAffected > 0;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ignore) {}
            e.printStackTrace();
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }
    
    public void updateProfilePic(String email, String path) {
    Connection conn = dbConnection.openConnection();
    String query = "UPDATE users SET profile_pic = ? WHERE email = ?";
    try {
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, path);
        stmt.setString(2, email);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        dbConnection.closeConnection(conn);
    }
}

// Get profile picture path for a user
public String getProfilePic(String email) {
    Connection conn = dbConnection.openConnection();
    String query = "SELECT profile_pic FROM users WHERE email = ?";
    try {
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("profile_pic");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        dbConnection.closeConnection(conn);
    }
    return null;
}
}