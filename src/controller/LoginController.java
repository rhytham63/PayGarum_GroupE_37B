package controller;

import DAO.DAO;
import Model.Session;

public class LoginController {
    private DAO dao;

    public LoginController() {
        this.dao = new DAO();
    }

    public boolean logIn(String email, String password) {
        String result = dao.logIn(email, password);
        if (result != null) {
            Session.loggedInUserEmail = email;
            return true;
        }
        return false;
    }
}