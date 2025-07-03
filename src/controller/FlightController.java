package controller;

import DAO.DAO;
import DAO.FlightDAO;
import DAO.TransactionHistoryDAO;
import Database.*;
import Model.Flightbooking;
import Model.Session;
import Model.TransactionHistory;
import View.Dashboard;
import java.sql.Connection;
import javax.swing.*;

public class FlightController {

    private final FlightDAO flightDAO;
    private final DAO dao;
    private final Dashboard dashboardRef;

    public FlightController(Dashboard dashboardRef) {
        this.flightDAO = new FlightDAO();
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
    boolean success = flightDAO.saveSelection(flight, email);

   if (success) {
    // ✅ Add to transaction history as Debit
    try (Connection historyConn = new MySqlConnection().openConnection()) {
        TransactionHistoryDAO historyDAO = new TransactionHistoryDAO(historyConn);
        double updatedBalance = dao.getBalance(email);
        TransactionHistory txn = new TransactionHistory(
            "Debit",
            ticketCost,
            updatedBalance,
            TransactionHistory.getCurrentDateTime(),
            "Flight booking: " + departure + " to " + arrival
        );
        historyDAO.addTransaction(email, txn);
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    JOptionPane.showMessageDialog(null, "Flight booked successfully! Rs. 6200 deducted.");
} else {
    JOptionPane.showMessageDialog(null, "Failed to save flight booking.");
    dao.addMoney(email, ticketCost); // rollback
}
}
}