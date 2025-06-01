package controller;

import DAO.DAO;

public class LoadMoneyController {
    private final DAO dao;

    public LoadMoneyController() {
        this.dao = new DAO();
    }

    public boolean loadMoney(String email, double amount, String password) {
        if (dao.logIn(email, password)) {
            return dao.addMoney(email, amount);
        } else {
            return false;
        }
    }
}
