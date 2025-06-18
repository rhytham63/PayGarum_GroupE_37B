package controller;

import DAO.DAO;
import View.LoadMoney;

import javax.swing.*;

public class LoadMoneyController {
    private final LoadMoney screen;
    private final String email;
    private final DAO dao;

    public LoadMoneyController(LoadMoney screen, String email) {
        this.screen = screen;
        this.email = email;
        this.dao = new DAO();
        setupButtonClick();
    }

    private void setupButtonClick() {
        screen.getButton().addActionListener(e -> handleAddMoney());
    }
    
    private void handleAddMoney() {
    try {
        double amount = Double.parseDouble(screen.getValue().getText());
        String password = new String(screen.getPasswordValue().getPassword());

        if (dao.logIn(email, password) != null) {
            if (dao.addMoney(email, amount)) {
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
    }
}


}
