package controller;

import View.LogoutUI;
import View.Registration;
import View.Dashboard;
import Model.Session;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutController {
    private final LogoutUI screen;
    private final Dashboard dashboard;

    public LogoutController(LogoutUI screen, Dashboard dashboard) {
        this.screen = screen;
        this.dashboard = dashboard;
        setupLogoutButton();
    }

    private void setupLogoutButton() {
        screen.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
    }

    private void handleLogout() {
        if (dashboard != null) {
            dashboard.dispose(); // close dashboard
        }

        screen.dispose(); // close logout UI
        Session.loggedInUserEmail = null; // clear session
        new Registration().setVisible(true); // open registration
    }
}
