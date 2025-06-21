package controller;

import DAO.DAO;
import DAO.TransactionHistoryDAO;
import Database.MySqlConnection;
import View.FundTransfer;
import model.TransactionHistory;
import javax.swing.JPasswordField;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class FundTransferController {
    private final FundTransfer view;
    private final String senderEmail;
    private final DAO dao;
    private static final double MIN_TRANSFER_AMOUNT = 1.0;
    private static final double MAX_TRANSFER_AMOUNT = 50000.0;

    public FundTransferController(FundTransfer view, String senderEmail) {
        this.view = view;
        this.senderEmail = senderEmail;
        this.dao = new DAO();
        setupListeners();
    }

    private void setupListeners() {
        view.addTransferListener(new TransferHandler());
    }

    private class TransferHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Connection conn = null;
            char[] passwordChars = null;

            try {
                // Fetch inputs
                String recipientEmail = view.getRecipientEmailField().getText().trim();
                String amountText = view.getAmountField().getText().trim();

                passwordChars = view.getPasswordField().getPassword();
                String password = new String(passwordChars).trim();

                // Validate
                if (recipientEmail.isEmpty() || password.isEmpty() || amountText.isEmpty()) {
                    showError("All fields are required.");
                    return;
                }

                double amount;
                try {
                    amount = Double.parseDouble(amountText);
                } catch (NumberFormatException ex) {
                    showError("Invalid amount format.");
                    return;
                }

                if (amount < MIN_TRANSFER_AMOUNT) {
                    showError("Minimum transfer amount is ₹" + MIN_TRANSFER_AMOUNT);
                    return;
                }

                if (amount > MAX_TRANSFER_AMOUNT) {
                    showError("Maximum transfer amount is ₹" + MAX_TRANSFER_AMOUNT);
                    return;
                }

                if (senderEmail.equalsIgnoreCase(recipientEmail)) {
                    showError("You cannot transfer to yourself.");
                    return;
                }

                // Transfer logic
                conn = MySqlConnection.getConnection();
                if (conn == null) {
                    showError("Database connection failed.");
                    return;
                }

                if (dao.transferMoney(senderEmail, recipientEmail, amount, password)) {
                    recordTransactions(conn, recipientEmail, amount);
                    showSuccess("Transfer successful!");
                    view.setVisible(false);
                } else {
                    showError("Transfer failed. Please check your credentials and balance.");
                }

            } catch (SQLException ex) {
                showError("Database error: " + ex.getMessage());
            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
            } finally {
                if (passwordChars != null) {
                    Arrays.fill(passwordChars, '\0');
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        System.err.println("Error closing connection: " + ex.getMessage());
                    }
                }
            }
        }

        private void recordTransactions(Connection conn, String recipientEmail, double amount) throws SQLException {
            TransactionHistoryDAO historyDAO = new TransactionHistoryDAO(conn);

            double senderBalance = dao.getBalance(senderEmail);
            TransactionHistory senderTxn = new TransactionHistory(
                    "Fund Transfer", amount, senderBalance,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    "Sent to " + recipientEmail
            );
            historyDAO.addTransaction(senderEmail, senderTxn);

            double recipientBalance = dao.getBalance(recipientEmail);
            TransactionHistory recipientTxn = new TransactionHistory(
                    "Fund Received", amount, recipientBalance,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    "Received from " + senderEmail
            );
            historyDAO.addTransaction(recipientEmail, recipientTxn);
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(view, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}