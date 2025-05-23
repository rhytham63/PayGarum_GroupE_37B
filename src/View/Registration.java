/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import controller.controller;
import javax.swing.JOptionPane;



/**
 *
 * @author r4hul
 */
public class Registration extends javax.swing.JFrame {

    /**
     * Creates new form Registration
     */
    public Registration() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FrameBox = new javax.swing.JPanel();
        greeting = new javax.swing.JLabel();
        num = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        text1 = new javax.swing.JLabel();
        text2 = new javax.swing.JLabel();
        registerButton = new javax.swing.JButton();
        num1 = new javax.swing.JTextField();
        text4 = new javax.swing.JLabel();
        DateOfBirth_Days = new javax.swing.JComboBox<>();
        text5 = new javax.swing.JLabel();
        DateOfBirth_Year = new javax.swing.JComboBox<>();
        DateOfBirth_Months = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        FrameBox.setBackground(new java.awt.Color(212, 235, 253));
        FrameBox.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        greeting.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        greeting.setText("Welcome to PayGarum !");

        num.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numActionPerformed(evt);
            }
        });

        pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passActionPerformed(evt);
            }
        });

        text1.setText("Full Name");

        text2.setText("Email");

        registerButton.setBackground(new java.awt.Color(77, 91, 146));
        registerButton.setForeground(new java.awt.Color(255, 255, 255));
        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        num1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                num1ActionPerformed(evt);
            }
        });

        text4.setText("Date of Birth");

        DateOfBirth_Days.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        DateOfBirth_Days.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DateOfBirth_DaysActionPerformed(evt);
            }
        });

        text5.setText("Password");

        DateOfBirth_Year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010" }));
        DateOfBirth_Year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DateOfBirth_YearActionPerformed(evt);
            }
        });

        DateOfBirth_Months.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        DateOfBirth_Months.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DateOfBirth_MonthsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FrameBoxLayout = new javax.swing.GroupLayout(FrameBox);
        FrameBox.setLayout(FrameBoxLayout);
        FrameBoxLayout.setHorizontalGroup(
            FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FrameBoxLayout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FrameBoxLayout.createSequentialGroup()
                        .addComponent(text4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DateOfBirth_Days, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(FrameBoxLayout.createSequentialGroup()
                                .addComponent(DateOfBirth_Months, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DateOfBirth_Year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(FrameBoxLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(FrameBoxLayout.createSequentialGroup()
                                .addComponent(text5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(FrameBoxLayout.createSequentialGroup()
                                .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(FrameBoxLayout.createSequentialGroup()
                                        .addComponent(text1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(FrameBoxLayout.createSequentialGroup()
                                        .addComponent(text2)
                                        .addGap(28, 28, 28)))
                                .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(greeting, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(num, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(num1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(110, 110, 110))
        );
        FrameBoxLayout.setVerticalGroup(
            FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FrameBoxLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(greeting, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text1)
                    .addComponent(num, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FrameBoxLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text2)
                            .addComponent(num1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FrameBoxLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text5))
                        .addGap(18, 18, 18)))
                .addGroup(FrameBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DateOfBirth_Days, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text4)
                    .addComponent(DateOfBirth_Months, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DateOfBirth_Year, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(registerButton)
                .addGap(69, 69, 69))
        );

        getContentPane().add(FrameBox);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DateOfBirth_MonthsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DateOfBirth_MonthsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DateOfBirth_MonthsActionPerformed

    private void DateOfBirth_YearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DateOfBirth_YearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DateOfBirth_YearActionPerformed

    private void DateOfBirth_DaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DateOfBirth_DaysActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DateOfBirth_DaysActionPerformed

    private void num1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_num1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_num1ActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        // TODO add your handling code here:
                try {
            // 1. Get values from form fields with your specific names
            String fullName = num.getText();
            String email = num1.getText();
            String password = new String(pass.getPassword());
            String day = DateOfBirth_Days.getSelectedItem().toString();
            String month = DateOfBirth_Months.getSelectedItem().toString();
            String year = DateOfBirth_Year.getSelectedItem().toString();

            // 2. Validate inputs
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill all fields", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Process registration through controller
            controller ControllerObj = new controller(); // ✅ create an instance
            boolean success = ControllerObj.registerUser(
                fullName, 
                email, 
                password, 
                day, 
                month, 
                year
            );

            // 4. Show result
            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Registration failed. Email may already exist.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + ex.getMessage(), 
                "Exception", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_registerButtonActionPerformed

    private void passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passActionPerformed

    private void numActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numActionPerformed

    /**
     * @param args the command line arguments
     */
    private void clearForm() {
        num.setText("");
        num1.setText("");
        pass.setText("");
        DateOfBirth_Days.setSelectedIndex(0);
        DateOfBirth_Months.setSelectedIndex(0);
        DateOfBirth_Year.setSelectedIndex(0);
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Registration().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> DateOfBirth_Days;
    private javax.swing.JComboBox<String> DateOfBirth_Months;
    private javax.swing.JComboBox<String> DateOfBirth_Year;
    private javax.swing.JPanel FrameBox;
    private javax.swing.JLabel greeting;
    private javax.swing.JTextField num;
    private javax.swing.JTextField num1;
    private javax.swing.JPasswordField pass;
    private javax.swing.JButton registerButton;
    private javax.swing.JLabel text1;
    private javax.swing.JLabel text2;
    private javax.swing.JLabel text4;
    private javax.swing.JLabel text5;
    // End of variables declaration//GEN-END:variables
}
