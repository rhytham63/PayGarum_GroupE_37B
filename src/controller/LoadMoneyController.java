package controller;

import DAO.DAO;
import View.LoadMoney;
import javax.swing.JOptionPane;

public class LoadMoneyController {
    private final LoadMoney view;
    private final String userEmail;
    private final DAO dao;

    public LoadMoneyController(LoadMoney view, String userEmail) {
        this.view = view;
        this.userEmail = userEmail;
        this.dao = new DAO();

        // Attach the listener here
        initListeners();
    }

    private void initListeners() {
        view.getButton().addActionListener(e -> handleSubmit());
    }

    private void handleSubmit() {
        try {
            double amount = Double.parseDouble(view.getValue().getText());
            String password = new String(view.getPasswordValue().getPassword());

            
                if (dao.addMoney(userEmail, amount)) {
                    JOptionPane.showMessageDialog(view, "Money added successfully");
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add money");
                }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }
}
