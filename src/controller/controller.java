package controller;

import DAO.DAO;
import Model.User;
import View.Login_page;
import View.Registration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class controller {
    private final DAO dao;

    public controller() {
        this.dao = new DAO();
    }

    // For registration navigation
    public void navigateToLogin(JFrame currentFrame) {
        Login_page loginFrame = new Login_page();
        loginFrame.setVisible(true);
        currentFrame.dispose();
    }

    // For registration handling
    public void handleRegistration(JFrame currentFrame,
                                 JTextField fullNameField,
                                 JTextField emailField,
                                 JPasswordField passwordField,
                                 JComboBox<String> dayCombo,
                                 JComboBox<String> monthCombo,
                                 JComboBox<String> yearCombo) {
        try {
            // Get values from fields
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String day = dayCombo.getSelectedItem().toString();
            String month = monthCombo.getSelectedItem().toString();
            String year = yearCombo.getSelectedItem().toString();

            // Validate inputs
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showError(currentFrame, "Please fill all fields");
                return;
            }

            // Process registration
            if (registerUser(fullName, email, password, day, month, year)) {
                JOptionPane.showMessageDialog(currentFrame, "Registration successful!");
                clearForm(fullNameField, emailField, passwordField, dayCombo, monthCombo, yearCombo);
                navigateToLogin(currentFrame);
            } else {
                showError(currentFrame, "Registration failed. Email may already exist.");
            }
        } catch (Exception ex) {
            showError(currentFrame, "Error: " + ex.getMessage());
        }
    }

    private boolean registerUser(String fullName, String email, String password,
                               String day, String month, String year) {
        try {
            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(password);

            String dateString = day + " " + month + " " + year;
            Date dateOfBirth = new SimpleDateFormat("dd MMMM yyyy").parse(dateString);
            user.setDateOfBirth(dateOfBirth);

            return dao.registerUser(user);
        } catch (ParseException e) {
            System.err.println("Date parsing error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    private void clearForm(JTextField fullNameField,
                         JTextField emailField,
                         JPasswordField passwordField,
                         JComboBox<String> dayCombo,
                         JComboBox<String> monthCombo,
                         JComboBox<String> yearCombo) {
        fullNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
    }

    private void showError(JFrame parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}