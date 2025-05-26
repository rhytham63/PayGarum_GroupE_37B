package controller;

import DAO.DAO;
import Model.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class controller {
    private DAO dao;
    
    public controller() {
        this.dao = new DAO();
    }
    
    
    public controller(DAO dao) {
        this.dao = dao;
    }
    
    public boolean registerUser(String fullName, String email, String password, 
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
            System.out.println("Date parsing error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }
}