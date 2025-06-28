package controller;

import DAO.DAO;
import DAO.FlightDAO;
import Database.MySqlConnection;
import Model.Flightbooking;
import Model.Session;
import View.Dashboard;

import javax.swing.*;
import java.sql.Connection;

public class FlightController {

    private final FlightDAO flightDAO;
    private final DAO dao;
    private final Dashboard dashboardRef;

    public FlightController(Connection connection, Dashboard dashboardRef) {
        this.flightDAO = new FlightDAO(connection);
        this.dao = new DAO();
        this.dashboardRef = dashboardRef;
    }

    public void processSelection(String departure, String arrival, String date) {
        String email = Session.loggedInUserEmail;
        double ticketCost = 6200;
        double currentBalance = dao.getBalance(email);

        if (currentBalance < ticketCost) {
            JOptionPane.showMessageDialog(null, "Insufficient balance to book flight (Rs. 6200 needed).");
            return;
        }

        boolean balanceDeducted = dao.addMoney(email, -ticketCost);

        if (!balanceDeducted) {
            JOptionPane.showMessageDialog(null, "Failed to deduct balance. Booking cancelled.");
            return;
        }

        Flightbooking flight = new Flightbooking(departure, arrival, date);
        boolean success = flightDAO.saveSelection(flight);

        if (success) {
            JOptionPane.showMessageDialog(null, "Flight booked successfully! Rs. 6200 deducted.");
            if (dashboardRef != null) {
                dashboardRef.refreshBalance(); // still fires the original method

                // ⚡ Additionally, force update to label directly
                JLabel balanceLabel = dashboardRef.getBalanceLabel(); // Make sure this method exists and returns your JLabel
                double updatedBalance = dao.getBalance(email);
                balanceLabel.setText("Rs. " + updatedBalance);
                balanceLabel.repaint();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Failed to save flight booking.");
            dao.addMoney(email, ticketCost); // rollback
        }
    }
}