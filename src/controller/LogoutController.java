/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.LogoutDAO;

/**
 *
 * @author utpre
 */
public class LogoutController {
    private final LogoutDAO logoutDAO;

    // Constructor initializes DAO
    public LogoutController() {
        this.logoutDAO = new LogoutDAO();
    }

    // Handles the logout action
    public void handleLogout() {
        logoutDAO.logoutUser();
    }


}
