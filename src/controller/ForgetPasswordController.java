package controller;

import DAO.DAO;
import View.ForgetPassword;
import javax.swing.*;

public class ForgetPasswordController {
    private final ForgetPassword view;
    private final DAO dao;
    private final String email;

    public ForgetPasswordController(ForgetPassword view, String email) {
        this.view = view;
        this.dao = new DAO();
        this.email = email;
        setup();
    }

    private void setup() {
        // Display the user's security question
        String questionText = dao.getSecurityQuestion(email);
        view.question.setText(questionText != null ? questionText : "No question set.");

        // Handle change password button
        view.changePass.addActionListener(e -> {
            String answer = view.ans.getText();
            String newPass = new String(view.pass.getPassword());

            if (answer.isEmpty() || newPass.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all fields");
                return;
            }

            if (dao.checkSecurityAnswer(email, answer)) {
                boolean changed = dao.updatePassword(email, newPass);
                if (changed) {
                    JOptionPane.showMessageDialog(view, "Password changed successfully");
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to change password");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Security answer incorrect");
            }
        });
    }
}