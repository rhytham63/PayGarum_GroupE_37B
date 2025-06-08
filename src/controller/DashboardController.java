/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import Database.*;
import View.*;

public class DashboardController {
    private final String email;
    private final Dashboard dashboard;

    public DashboardController(String email, Dashboard dashboard) {
        this.email = email;
        this.dashboard = dashboard;
    }

    public void loadUserBalance(JLabel balanceLabel) {
        try {
            Connection conn = new MySqlConnection().openConnection();
            if (conn != null) {
                String query = "SELECT balance FROM users WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    balanceLabel.setText("Rs " + balance);
                } else {
                    balanceLabel.setText("Balance: Not found");
                }
                conn.close();
            } else {
                JOptionPane.showMessageDialog(dashboard, "Database connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(dashboard, "Error loading balance: " + e.getMessage());
        }
    }

    public void openLoadMoneyWindow() {
        new LoadMoney(email, dashboard).setVisible(true);
    }

    public void refreshBalance(JLabel balanceLabel) {
        loadUserBalance(balanceLabel);
    }
}
