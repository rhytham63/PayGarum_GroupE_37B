package controller;

import DAO.DAO;
import View.FundTransfer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class TransferMoneyController {
    private final FundTransfer view;
    private final String userEmail;
    private final DAO dao  = new DAO();

    public TransferMoneyController(FundTransfer view, String userEmail) {
        this.view = view;
        this.userEmail = userEmail;
        this.view.addTransferListener(new  handleTansfer());
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
                JOptionPane.showMessageDialog(view, "Transfer successful!");
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Transfer failed. Check recipient email, balance, or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
    }
}