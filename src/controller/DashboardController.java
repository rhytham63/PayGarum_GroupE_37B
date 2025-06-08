
package controller;


import java.sql.*;
import javax.swing.*; 
import Database.*; 
import View.*; 


public class DashboardController {

    private final String userEmail;
    private final Dashboard dashboardScreen;


    public DashboardController(String email, Dashboard dashboard) {
        this.userEmail = email;
        this.dashboardScreen = dashboard;
    }

    public void loadUserBalance(JLabel balanceLabel) {
        try {
          
            MySqlConnection dbConnection = new MySqlConnection();
            Connection conn = dbConnection.openConnection();

     
            if (conn != null) {
                String sql = "SELECT balance FROM users WHERE email = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, userEmail); 

             
                ResultSet result = statement.executeQuery();

              
                if (result.next()) {
                    double balance = result.getDouble("balance");
                    balanceLabel.setText("Rs " + balance); // Show the balance
                } else {
                    balanceLabel.setText("Balance: Not found");
                }

                conn.close();

            } else {
                JOptionPane.showMessageDialog(dashboardScreen, "Could not connect to the database.");
            }

        } catch (Exception e) {
       
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(dashboardScreen, "Something went wrong: " + e.getMessage());
        }
    }


    public void openLoadMoneyWindow() {
        LoadMoney loadMoneyWindow = new LoadMoney(userEmail, dashboardScreen);
        loadMoneyWindow.setVisible(true);
    }
    
    public void refreshBalance(JLabel balanceLabel) {
        loadUserBalance(balanceLabel);
    }
}
