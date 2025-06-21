package controller;

import DAO.DAO;
import DAO.NotificationDAO;
import Database.*;
import View.FundTransfer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.Connection;


public class TransferMoneyController {
    private final FundTransfer view;
    private final String userEmail;
    private final DAO dao  = new DAO();
    private final DashboardController c;

    public TransferMoneyController(DashboardController c, FundTransfer view, String userEmail) {
        this.c = c;
        this.view = view;
        this.userEmail = userEmail;
        this.view.addTransferListener(new  handleTansfer());
    
    }

    public void open(){
        this.view.setVisible(true);
    }
    
    public void close(){
        this.view.setVisible(false);
    }



 
    private  class handleTansfer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
            String recipientEmail = view.getRecipientEmailField().getText().trim();
            double amount = Double.parseDouble(view.getAmountField().getText().trim());
            String password = new String(view.getPasswordField().getText().trim());

            // Validate inputs
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

            // Perform transfer
                if (dao.transferMoney(userEmail, recipientEmail, amount, password)) {
                    NotificationDAO ndao = new NotificationDAO();
                    Connection conn = MySQLNotification.getConnection();

                   ndao.addNotification(conn, userEmail + " sent Rs " + amount + " to " + recipientEmail, userEmail);
                    ndao.addNotification(conn, "You received Rs " + amount + " from " + userEmail, recipientEmail);


                    MySQLNotification.close(conn);

                    JOptionPane.showMessageDialog(view, "Transfer successful!");
                    c.loadUserBalance();
                    view.dispose();
                }
            } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
    }

}