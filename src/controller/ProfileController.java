package controller;

import DAO.DAO;
import Model.User;
import View.customer_supp;
import View.profile;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class profileController {
    private final DAO dao = new DAO();
    private final profile p;
    private final String email;
    private User user;
   

    public profileController(profile p, String email) {
        this.p = p;
        this.email = email;
        setValues();
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
    
    public void openSecurityWindow(){
     
        
    }
    
    public void openSupportWindow(){
       
    
        p.getcustomerSupportBtn().addActionListener((ActionEvent e) -> {
            openSupportWindow();
        });
        }
    }

