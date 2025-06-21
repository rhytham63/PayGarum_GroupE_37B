package controller;

import DAO.DAO;
import Model.Session;
import View.Reset_Password;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetPasswordController {
    private Reset_Password view;
    private DAO dao;

    public ResetPasswordController(Reset_Password view) {
        this.view = view;
        this.dao = new DAO();
        setupListener();
    }

    private void setupListener() {
        view.getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPass = view.getPrevPass().getText();
                String newPass = new String(view.getNewPass().getPassword());
                String email = Session.loggedInUserEmail;

                if (oldPass.isEmpty() || newPass.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please fill all fields");
                    return;
                }

                boolean success = dao.resetPassword(email, oldPass, newPass);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Password changed successfully");
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Old password incorrect");
                }
            }
        });
    }
}
