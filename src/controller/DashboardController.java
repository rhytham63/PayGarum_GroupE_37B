package controller;

import DAO.DAO;
import DAO.NotificationDAO;

import Database.*;
import Model.Session;
import Model.User;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DashboardController {
    private final User currentUser;
    private final Dashboard dashboardScreen;
    private final DAO dao;

    public DashboardController(String email, Dashboard dashboard) {
        this.dashboardScreen = dashboard;
        this.dao = new DAO();
        this.currentUser = new User();
        this.currentUser.setEmail(email);
        Session.loggedInUserEmail = email;

        initializeController();

        this.dashboardScreen.addTransferListener(new TransferFund());
        openProfileWindow(); // ⬅️ This opens profile, which now handles reset/delete
    }
private void initializeController() {
    loadUserBalance();

    // Load all transactions on startup
    new TransactionHistoryController(dashboardScreen, currentUser.getEmail())
        .loadTransactionHistory(dashboardScreen.getTransactionTable());

    // Filter combo box handling
    dashboardScreen.getFilterComboBox().addActionListener(e -> {
        String selected = (String) dashboardScreen.getFilterComboBox().getSelectedItem();
        if (selected != null) {
            new TransactionHistoryController(dashboardScreen, currentUser.getEmail())
                .loadFilteredTransactions(dashboardScreen.getTransactionTable(), selected);
        }
    });

    dashboardScreen.getCurrencyConvert().addActionListener(e -> {
        new CurrencyConverterUI().setVisible(true);
    });

    dashboardScreen.getLogoutBtn().addActionListener(e -> {
        new LogoutUI(dashboardScreen).setVisible(true);
    });
}


    public void initializeEventButtons() {
        JButton[] eventButtons = dashboardScreen.getEventButtons();
        for (JButton button : eventButtons) {
            String eventName = button.getActionCommand();
            if (dao.hasBookedEvent(currentUser.getEmail(), eventName)) {
                button.setText("Booked");
                button.setEnabled(false);
            }
        }
    }

    public void loadUserBalance() {
    double balance = dao.getBalance(currentUser.getEmail());
    currentUser.setBalance(balance);
    updateBalanceDisplay();
}

    private double loadBalanceFromDB(String email) throws SQLException {
        Connection conn = new MySqlConnection().openConnection();
        if (conn == null) throw new SQLException("Database connection failed");

        String query = "SELECT balance FROM users WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        double balance = 0.0;
        if (rs.next()) {
            balance = rs.getDouble("balance");
        }
        conn.close();
        return balance;
    }

    private void updateBalanceDisplay() {
        String balanceText = "Rs " + currentUser.getBalance();
        Component[] components = dashboardScreen.getContentPane().getComponents();
        boolean updated = false;
        for (Component comp : components) {
            if (comp instanceof JLabel && "balanceLabel".equals(comp.getName())) {
                ((JLabel) comp).setText(balanceText);
                updated = true;
                break;
            }
        }

        if (!updated) {
            try {
                dashboardScreen.getBalanceLabel().setText(balanceText);
            } catch (Exception ignored) {}
        }
    }

    public void refreshBalance() {
        loadUserBalance();
    }

    public void openLoadMoneyWindow() {
    new LoadMoney(currentUser.getEmail(), dashboardScreen, this).setVisible(true);
}

    public void handleEventBooking(String eventName, double price, JButton bookButton) {
        try {
            if (dao.hasBookedEvent(currentUser.getEmail(), eventName)) {
                bookButton.setEnabled(false);
                bookButton.setText("Booked");
                showInfo("You already booked this event");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    dashboardScreen,
                    "Book " + eventName + " for Rs " + price + "?",
                    "Confirm Booking",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                if (dao.bookEvent(currentUser.getEmail(), eventName, price)) {
                    currentUser.setBalance(currentUser.getBalance() - price);
                    updateBalanceDisplay();

                    bookButton.setEnabled(false);
                    bookButton.setText("Booked");

                    // ✅ ADD NOTIFICATION
                    Connection conn = MySQLNotification.getConnection();
                    new NotificationDAO().addNotification(conn, "You booked " + eventName + " for Rs " + price, currentUser.getEmail());


                    showInfo("Booking successful!");
                } else {
                    showError("Booking failed - insufficient balance");
                }
            }
        } catch (Exception e) {
            showError("Error during booking: " + e.getMessage());
        }
        
    }

public void openProfileWindow() {
    dashboardScreen.getProfileButton().addActionListener((ActionEvent e) -> {
        profile p = new profile();
        profileController profileCtrl = new profileController(p, currentUser.getEmail(), dashboardScreen);
        profileCtrl.open();
    });
}

    private void showError(String message) {
        JOptionPane.showMessageDialog(dashboardScreen, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(dashboardScreen, message);
    }

    private class TransferFund implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FundTransfer transferView = new FundTransfer();
            TransferMoneyController controller = new TransferMoneyController(
                    DashboardController.this, transferView, currentUser.getEmail()
            );
            controller.open();
        }
    }
}
