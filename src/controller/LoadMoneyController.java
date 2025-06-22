package controller;

import DAO.DAO;
import DAO.NotificationDAO;
import DAO.TransactionHistoryDAO;
import Database.MySQLNotification;
import Database.MySqlConnection;
import Model.TransactionHistory;
import View.LoadMoney;

import javax.swing.*;
import java.sql.Connection;

public class LoadMoneyController {
    private final LoadMoney screen;
    private final String email;
    private final DAO dao;
    private final DashboardController dashboardController;

    public LoadMoneyController(LoadMoney screen, String email, DashboardController dashboardController) {
        this.screen = screen;
        this.email = email;
        this.dao = new DAO();
        this.dashboardController = dashboardController;
        setupButtonClick();
    }

    private void setupButtonClick() {
        screen.getButton().addActionListener(e -> handleAddMoney());
    }

    private void handleAddMoney() {
        try {
            double amount = Double.parseDouble(screen.getValue().getText());
            if (amount > 50000) {
                JOptionPane.showMessageDialog(screen, "Don't add more than your limit (50,000)");
                return;
            }

            String password = new String(screen.getPasswordValue().getPassword());

            if (dao.logIn(email, password) != null) {
                if (dao.addMoney(email, amount)) {

                    // ✅ Add to transaction_history table
                    double updatedBalance = dao.getBalance(email); // get current balance after update
                    TransactionHistory txn = new TransactionHistory(
                            "Credit",
                            amount,
                            updatedBalance,
                            TransactionHistory.getCurrentDateTime(),
                            "Money loaded to wallet"
                    );

                    Connection historyConn = new MySqlConnection().openConnection();
                    TransactionHistoryDAO historyDAO = new TransactionHistoryDAO(historyConn);
                    historyDAO.addTransaction(email, txn);
                    historyConn.close();

                    // ✅ Notification
                    Connection conn = MySQLNotification.getConnection();
                    NotificationDAO ndao = new NotificationDAO();
                    ndao.addNotification(conn, email + " loaded Rs " + amount, email);
                    MySQLNotification.close(conn);

                    dashboardController.loadUserBalance();
                    JOptionPane.showMessageDialog(screen, "Money added successfully");
                    screen.dispose();

                } else {
                    JOptionPane.showMessageDialog(screen, "Failed to add money");
                }
            } else {
                JOptionPane.showMessageDialog(screen, "Incorrect password");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(screen, "Invalid amount");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(screen, "Error: " + e.getMessage());
        }
    }
}
