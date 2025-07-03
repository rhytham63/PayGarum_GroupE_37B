package controller;

import DAO.DAO;
import Model.User;
import View.Dashboard;
import View.Registration;
import View.Reset_Password;
import View.customer_supp;
import View.profile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class profileController {
    private final DAO dao = new DAO();
    private final profile p;
    private final String email;
    private final JFrame dashboard;
    private User user;

    public profileController(profile p, String email, JFrame dashboard) {
        this.p = p;
        this.email = email;
        this.dashboard = dashboard;
        setValues();
        initializeActions();
        setupProfilePicListener();
        loadProfilePic();
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
                    p.dispose();
                    if (dashboard != null) {
                        dashboard.dispose();
                    }
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

    // --- Profile Picture Logic ---
    private void setupProfilePicListener() {
        p.profilePic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(p);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    String destDir = "Images";
                    String destPath = destDir + "/" + email + ".png";
                    try {
                        new File(destDir).mkdirs();
                        Files.copy(selectedFile.toPath(), new File(destPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        dao.updateProfilePic(email, destPath);
                        setProfilePic(destPath);
                        JOptionPane.showMessageDialog(p, "Profile picture updated!");
                        // Optionally update on dashboard if it has a profile button
                            if (dashboard != null && dashboard instanceof Dashboard) {
                                JButton profileBtn = ((Dashboard) dashboard).getProfileButton();
                                if (profileBtn != null) {
                                    setButtonIcon(profileBtn, destPath);
                                }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(p, "Failed to set profile picture: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void loadProfilePic() {
        String picPath = dao.getProfilePic(email);
        if (picPath != null && !picPath.isEmpty()) {
            setProfilePic(picPath);
        }
    }

    private void setProfilePic(String path) {
        ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(
                p.profilePic.getWidth(), p.profilePic.getHeight(), java.awt.Image.SCALE_SMOOTH));
        p.profilePic.setIcon(icon);
    }

    // Utility for dashboard button (if you want to update it)
    private void setButtonIcon(AbstractButton btn, String path) {
        ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(
                btn.getWidth(), btn.getHeight(), java.awt.Image.SCALE_SMOOTH));
        btn.setIcon(icon);
    }
}