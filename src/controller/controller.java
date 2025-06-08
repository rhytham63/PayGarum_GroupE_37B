package controller;

import DAO.DAO;
import Model.User;
import View.Login_page;
import View.Registration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class controller {
    private final DAO dataAccess;

    public controller() {
        this.dataAccess = new DAO();
    }

    public void goToLogin(JFrame currentFrame) {
        Login_page login = new Login_page();
        login.setVisible(true);
        currentFrame.dispose();
    }

    public void handleRegister(JFrame currentFrame,
                               JTextField nameField,
                               JTextField emailField,
                               JPasswordField passField,
                               JComboBox<String> dayBox,
                               JComboBox<String> monthBox,
                               JComboBox<String> yearBox) {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passField.getPassword());
            String day = dayBox.getSelectedItem().toString();
            String month = monthBox.getSelectedItem().toString();
            String year = yearBox.getSelectedItem().toString();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showError(currentFrame, "Please fill all fields");
                return;
            }

            if (register(name, email, password, day, month, year)) {
                JOptionPane.showMessageDialog(currentFrame, "Registration successful!");
                clearForm(nameField, emailField, passField, dayBox, monthBox, yearBox);
                goToLogin(currentFrame);
            } else {
                showError(currentFrame, "Registration failed. Email may already exist.");
            }
        } catch (Exception e) {
            showError(currentFrame, "Error: " + e.getMessage());
        }
    }

    private boolean register(String name, String email, String password,
                             String day, String month, String year) {
        try {
            User newUser = new User();
            newUser.setFullName(name);
            newUser.setEmail(email);
            newUser.setPassword(password);

            String dateStr = day + " " + month + " " + year;
            Date dob = new SimpleDateFormat("dd MMMM yyyy").parse(dateStr);
            newUser.setDateOfBirth(dob);

            return dataAccess.registerUser(newUser);
        } catch (ParseException e) {
            System.err.println("Date format error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Registration problem: " + e.getMessage());
            return false;
        }
    }

    private void clearForm(JTextField nameField,
                           JTextField emailField,
                           JPasswordField passField,
                           JComboBox<String> dayBox,
                           JComboBox<String> monthBox,
                           JComboBox<String> yearBox) {
        nameField.setText("");
        emailField.setText("");
        passField.setText("");
        dayBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        yearBox.setSelectedIndex(0);
    }

    private void showError(JFrame frame, String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
