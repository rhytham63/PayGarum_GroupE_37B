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

    public void handleRegister(JFrame currentFrame, JTextField nameField, JTextField emailField, JPasswordField passField, JTextField gender, JComboBox<String> dayBox, JComboBox<String> monthBox, JComboBox<String> yearBox, ButtonGroup buttonGroup2) {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passField.getPassword());
            String day = dayBox.getSelectedItem().toString();
            String month = monthBox.getSelectedItem().toString();
            String year = yearBox.getSelectedItem().toString();
            String genderIpt = gender.getText();

            // Get account type using the getter methods
            String accountType = "";
            if (currentFrame instanceof Registration reg) {
                if (reg.getSavingRadioButton().isSelected()) {
                    accountType = "Saving";
                } else if (reg.getCurrentRadioButton().isSelected()) {
                    accountType = "Current";
                }
            }

            // Get security question and answer from Registration view
            String securityQuestion = "";
            String securityAnswer = "";
            if (currentFrame instanceof Registration reg) {
                securityQuestion = reg.getSecurityQuestion();
                securityAnswer = reg.getSecurityAnswer();
            }

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || accountType.isEmpty() ||
                securityQuestion.isEmpty() || securityAnswer.isEmpty()) {
                showError(currentFrame, "Please fill all fields, including security question and answer, and select an account type");
                return;
            }

            if (register(name, email, password, day, month, year, genderIpt, accountType, securityQuestion, securityAnswer)) {
                JOptionPane.showMessageDialog(currentFrame, "Registration successful!");
                clearForm(nameField, emailField, passField, dayBox, monthBox, yearBox, gender);
                goToLogin(currentFrame);
            } else {
                showError(currentFrame, "Registration failed. Email may already exist.");
                // Do NOT call goToLogin here!
            }
        } catch (Exception e) {
            showError(currentFrame, "Error: " + e.getMessage());
        }
    }

    // Updated register method to include security question and answer
    private boolean register(String name, String email, String password,
                             String day, String month, String year,
                             String genderIpt, String accountType,
                             String securityQuestion, String securityAnswer) {
        try {
            User newUser = new User();
            newUser.setFullName(name);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setgender(genderIpt);
            newUser.setAccountType(accountType);

            String dateStr = day + " " + month + " " + year;
            Date dob = new SimpleDateFormat("dd MMMM yyyy").parse(dateStr);
            newUser.setDateOfBirth(dob);

            // Pass security question and answer to DAO
            return dataAccess.registerUser(newUser, securityQuestion, securityAnswer);
        } catch (ParseException e) {
            System.err.println("Date format error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Registration problem: " + e.getMessage());
            return false;
        }
    }

    private void clearForm(JTextField nameField, JTextField emailField, JPasswordField passField,
                           JComboBox<String> dayBox, JComboBox<String> monthBox,
                           JComboBox<String> yearBox, JTextField genderIpt) {
        nameField.setText("");
        emailField.setText("");
        passField.setText("");
        dayBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        yearBox.setSelectedIndex(0);
        genderIpt.setText("");

        if (genderIpt.getTopLevelAncestor() instanceof Registration reg) {
            reg.getSavingRadioButton().setSelected(false);
            reg.getCurrentRadioButton().setSelected(false);
        }
    }

    private void showError(JFrame frame, String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}