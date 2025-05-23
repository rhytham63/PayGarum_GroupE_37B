/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.DAO;
import Model.User;
import View.Login_page;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author NITRO
 */
public class LoginController {
   private DAO dao;
    
    public LoginController() {
        this.dao = new DAO();
    }
    
    
    public LoginController(DAO dao) {
        this.dao = dao;
    }
    
    public boolean logIn(String email, String password) {
        try {
  
            dao.logIn(email,password);
            
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }finally{return true;}
    }
}
