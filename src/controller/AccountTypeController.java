package controller;

import DAO.AccountTypeDAO;
import View.AccountType;
import Model.AccountTypeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountTypeController {
    private AccountType view;
    private AccountTypeModel model;
    private AccountTypeDAO dao;

    public AccountTypeController(AccountType view) {
        this.view = view;
        this.model = new AccountTypeModel();
        this.dao = new AccountTypeDAO();

        this.view.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAccountSelection();
            }
        });
    }

    private void handleAccountSelection() {
        if (view.savingRadioButton.isSelected()) {
            model.setAccountType("Saving Account");
        } else if (view.currentRadioButton.isSelected()) {
            model.setAccountType("Current Account");
        } else if (view.salaryRadioButton.isSelected()) {
            model.setAccountType("Salary Account");
        } else {
            model.setAccountType("None");
        }

        dao.saveAccountType(model);
    }
}
