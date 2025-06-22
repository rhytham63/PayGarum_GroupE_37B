package controller;

import DAO.DAO;
import DAO.NotificationDAO;
import DAO.TransactionHistoryDAO;
import Database.*;
import Model.TransactionHistory;
import View.FundTransfer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.Connection;

public class TransferMoneyController {
    private final FundTransfer view;
    private final String userEmail;
    private final DAO dao = new DAO();
    private final DashboardController c;

    public TransferMoneyController(DashboardController c, FundTransfer view, String userEmail) {
        this.c = c;
        this.view = view;
        this.userEmail = userEmail;
        this.view.addTransferListener(new handleTransfer());
    }

    public void open() {
        this.view.setVisible(true);
    }

    public void close() {
        this.view.setVisible(false);
    }

    private class handleTransfer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String recipientEmail = view.getRecipientEmailField().getText().trim();
                double amount = Double.parseDouble(view.getAmountField().getText().trim());
                String password = view.getPasswordField().getText().trim();

                if (recipientEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Recipient email is required", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(view, "Amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Password is required", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (dao.transferMoney(userEmail, recipientEmail, amount, password)) {
                    // ✅ Send Notifications
                    Connection notifConn = MySQLNotification.getConnection();
                    NotificationDAO ndao = new NotificationDAO();
                    ndao.addNotification(notifConn, userEmail + " sent Rs " + amount + " to " + recipientEmail, userEmail);
                    ndao.addNotification(notifConn, "You received Rs " + amount + " from " + userEmail, recipientEmail);
                    MySQLNotification.close(notifConn);

                    // ✅ Save to transaction_history
                    Connection historyConn = new MySqlConnection().openConnection();
                    TransactionHistoryDAO historyDAO = new TransactionHistoryDAO(historyConn);

                    double updatedBalance = dao.getBalance(userEmail); // after transfer
                    double recipientBalance = dao.getBalance(recipientEmail); // recipient's new balance

                    TransactionHistory senderTxn = new TransactionHistory(
                            "Debit",
                            amount,
                            updatedBalance,
                            TransactionHistory.getCurrentDateTime(),
                            "Sent money to " + recipientEmail
                    );

                    TransactionHistory recipientTxn = new TransactionHistory(
                            "Credit",
                            amount,
                            recipientBalance,
                            TransactionHistory.getCurrentDateTime(),
                            "Received money from " + userEmail
                    );

                    historyDAO.addTransaction(userEmail, senderTxn);
                    historyDAO.addTransaction(recipientEmail, recipientTxn);
                    historyConn.close();

                    JOptionPane.showMessageDialog(view, "Transfer successful!");
                    c.loadUserBalance();
                    view.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Debugging
            }
        }
    }
}
