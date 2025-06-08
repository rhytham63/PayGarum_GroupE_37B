package controller;

import DAO.DAO;

public class LoginController {
    private DAO dao;

    public LoginController() {
        this.dao = new DAO();
    }

    public LoginController(DAO dao) {
        this.dao = dao;
    }

    public String logIn(String email, String password) {
        return dao.logIn(email, password);
    }
}
