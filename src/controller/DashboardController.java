package controller;

import DAO.DAO;
import Model.Session;
import View.Dashboard;
import View.LoadMoney;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardController {

    private final String userEmail;
    private final Dashboard dashboardScreen;
    private final DAO dao;

    public DashboardController(String email, Dashboard dashboard) {
        this.userEmail = email;
        this.dashboardScreen = dashboard;
        this.dao = new DAO();
    }

    // Load and show user balance in the label
    public void loadUserBalance(JLabel balanceLabel) {
        try {
            double balance = dao.getBalance(userEmail);
            balanceLabel.setText("Rs " + balance);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(dashboardScreen, "Error loading balance: " + e.getMessage());
        }
    }

    // Refresh balance label
    public void refreshBalance(JLabel balanceLabel) {
        loadUserBalance(balanceLabel);
    }

    // Open the Load Money form
    public void openLoadMoneyWindow() {
        LoadMoney loadMoneyWindow = new LoadMoney(userEmail, dashboardScreen);
        loadMoneyWindow.setVisible(true);
    }

    // Check if the user has already booked the event
    public boolean isEventBooked(String eventName) {
        return dao.hasBookedEvent(userEmail, eventName);
    }

    // Book the event if balance is sufficient, confirm popup
    public void handleEventBooking(String eventName, double price, JButton bookButton, JLabel balanceLabel) {
    // Prevent action if already hidden or disabled
    if (!bookButton.isVisible() || !bookButton.isEnabled()) return;

    if (dao.hasBookedEvent(userEmail, eventName)) {
        JOptionPane.showMessageDialog(dashboardScreen, "You have already booked this event.");
        bookButton.setEnabled(false);
        bookButton.setVisible(false);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
            dashboardScreen,
            "Do you want to book \"" + eventName + "\" for Rs " + price + "?",
            "Confirm Booking",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        boolean success = dao.bookEvent(userEmail, eventName, price);
        if (success) {
            JOptionPane.showMessageDialog(dashboardScreen, "Booking Successful!");

            // Disable then hide button to prevent double-trigger
            bookButton.setEnabled(false);
            bookButton.setVisible(false);

            refreshBalance(balanceLabel);
        } else {
            JOptionPane.showMessageDialog(dashboardScreen, "Booking failed. Not enough balance or an error occurred.");
        }
    }
}

    // Setup both event buttons: hide if booked and attach booking logic
public void setupEventButtons(JButton eventButton, JButton event1Button, JLabel balanceLabel) {
    if (dao.hasBookedEvent(userEmail, "event")) {
        eventButton.setText("Booked");
        eventButton.setEnabled(false);
    } else {
        eventButton.addActionListener(e -> {
            eventButton.setEnabled(false); 
            handleEventBooking("event", 1500, eventButton, balanceLabel);
        });
    }

    if (dao.hasBookedEvent(userEmail, "event1")) {
        event1Button.setText("Booked");
        event1Button.setEnabled(false);
    } else {
        event1Button.addActionListener(e -> {
            event1Button.setEnabled(false); 
            handleEventBooking("event1", 3000, event1Button, balanceLabel);
        });
    }
}
}
