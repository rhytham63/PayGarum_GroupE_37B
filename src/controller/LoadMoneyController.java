package controller;

import DAO.DAO;
import View.LoadMoney;
import javax.swing.JOptionPane;

public class LoadMoneyController {
    private final LoadMoney screen;
    private final String email;
    private final DAO dataAccess;

    public LoadMoneyController(LoadMoney screen, String email) {
        this.screen = screen;
        this.email = email;
        this.dataAccess = new DAO();
        setupButtonClick();
    }

    private void setupButtonClick() {
        screen.getButton().addActionListener(e -> handleAddMoney());
    }

    private void handleAddMoney() {
        try {
            String amountText = screen.getValue().getText();
            double amount = Double.parseDouble(amountText);
            String password = new String(screen.getPasswordValue().getPassword());

            if (dataAccess.logIn(email, password)) {
                if (dataAccess.addMoney(email, amount)) {
                    JOptionPane.showMessageDialog(screen, "Money added successfully");
                } else {
                    JOptionPane.showMessageDialog(screen, "Failed to add money");
                }
            } else {
                JOptionPane.showMessageDialog(screen, "Incorrect password");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(screen, "Error: " + e.getMessage());
        }
    }
}
