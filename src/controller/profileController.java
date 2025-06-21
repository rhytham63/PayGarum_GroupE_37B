package controller;

import DAO.DAO;
import Model.User;
import View.Registration;
import View.Reset_Password;
import View.customer_supp;
import View.profile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class profileController {
    private final DAO dao = new DAO();
    private final profile p;
    private final String email;
    private final JFrame dashboard; // Add reference to Dashboard
    private User user;

    public profileController(profile p, String email, JFrame dashboard) {
        this.p = p;
        this.email = email;
        this.dashboard = dashboard; // Store Dashboard reference
        setValues();
        initializeActions();
    }

    public void open() {
        this.p.setVisible(true);
    }

    public void close() {
        this.p.setVisible(false);
    }

    public final void setValues() {
        try {
            user = dao.getUserProfile(email);
            p.Display_email.setText(user.getEmail());
            p.Display_gender.setText(user.getGender());
            p.display_DOB.setText(user.getDateOfBirth().toString());
            p.Display_name.setText(user.getFullName());
        } catch (SQLException ex) {
            Logger.getLogger(profileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initializeActions() {
        // 🔒 Reset Password
        p.getSecurityButton().addActionListener((ActionEvent e) -> {
            new Reset_Password(email).setVisible(true);
        });

        // 🗑 Delete Account
        p.getDeleteButton().addActionListener((ActionEvent e) -> {
            int confirm = JOptionPane.showConfirmDialog(p,
                    "Are you sure you want to delete your account?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = dao.deleteUserAccount(email);
                if (deleted) {
                    JOptionPane.showMessageDialog(p, "Account deleted successfully.");

                    // ✅ Dispose both profile and dashboard
                    p.dispose(); // close profile
                    if (dashboard != null) {
                        dashboard.dispose(); // close Dashboard
                    }

                    // ✅ Redirect to Registration screen
                    new Registration().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(p, "Failed to delete account.");
                }
            }
        });

        // 📞 Customer Support
        p.getcustomerSupportBtn().addActionListener((ActionEvent e) -> {
            new customer_supp().setVisible(true);
        });
    }
}