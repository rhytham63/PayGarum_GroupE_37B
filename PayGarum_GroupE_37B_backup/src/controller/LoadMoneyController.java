package controller;

import DAO.DAO;
import DAO.TransactionHistoryDAO;
import Database.MySqlConnection;
import View.LoadMoney;
import model.TransactionHistory;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class LoadMoneyController {
    private final LoadMoney view;
    private final String email;
    private final DAO dao;
    private static final double MAX_LOAD_AMOUNT = 50000.0;

    public LoadMoneyController(LoadMoney view, String email) {
        this.view = view;
        this.email = email;
        this.dao = new DAO();
        setupButtonListener();
    }

    private void setupButtonListener() {
        view.getButton().addActionListener(e -> handleLoadMoney());
    }

    private void handleLoadMoney() {
        Connection conn = null;
        char[] passwordChars = null;
        
        try {
            // Get input values
            double amount = Double.parseDouble(view.getValue().getText().trim());
            
            // Secure password handling
            passwordChars = view.getPasswordValue().getPassword();
            String password = new String(passwordChars).trim();

            // Validate inputs
            if (password.isEmpty()) {
                showError("Password is required");
                return;
            }
            
            if (amount <= 0) {
                showError("Amount must be positive");
                return;
            }
            
            if (amount > MAX_LOAD_AMOUNT) {
                showError("Cannot load more than ₹" + MAX_LOAD_AMOUNT);
                return;
            }

            // Authenticate user
            if (dao.logIn(email, password) != null) {
                conn = MySqlConnection.getConnection();
                if (conn == null) {
                    showError("Database connection failed");
                    return;
                }

                // Add money to account
                if (dao.addMoney(email, amount)) {
                    // Record transaction
                    double newBalance = dao.getBalance(email);
                    recordTransaction(conn, amount, newBalance);
                    
                    showSuccess("Successfully loaded ₹" + amount);
                    view.dispose(); // Just close the window without refreshing dashboard
                } else {
                    showError("Failed to load money");
                }
            } else {
                showError("Incorrect password");
            }
        } catch (NumberFormatException e) {
            showError("Invalid amount format");
        } catch (SQLException e) {
            showError("Database error: " + e.getMessage());
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        } finally {
            // Securely clear password from memory
            if (passwordChars != null) {
                Arrays.fill(passwordChars, '\0');
            }
            // Close connection
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    private void recordTransaction(Connection conn, double amount, double newBalance) throws SQLException {
        TransactionHistoryDAO historyDAO = new TransactionHistoryDAO(conn);
        TransactionHistory txn = new TransactionHistory(
            "Load Money",
            amount,
            newBalance,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            "Wallet top-up"
        );
        historyDAO.addTransaction(email, txn);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(view, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}