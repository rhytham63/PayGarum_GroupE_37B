package controller;

import DAO.NotificationDAO;
import Database.MySQLNotification;
import Model.Notification;
import Model.Session;

import java.sql.Connection;
import java.util.List;

public class NotificationController {
    public final NotificationDAO dao = new NotificationDAO();

    public String getFormattedNotifications() {
        Connection conn = MySQLNotification.getConnection();
        StringBuilder display = new StringBuilder();
        String email = Session.loggedInUserEmail;

        if (conn == null) return "Unable to connect to database.";

        List<Notification> list = dao.getAllNotifications(conn, email);

        if (list == null || list.isEmpty()) {
            MySQLNotification.close(conn);
            return "No notifications found.";
        }

        for (Notification n : list) {
            display.append("- ").append(n.getMessage());
            if (!n.isRead()) {
                display.append(" (unread)");
            }
            display.append("\n");
        }

        MySQLNotification.close(conn);
        return display.toString();
    }

    public void markAllAsRead() {
        Connection conn = MySQLNotification.getConnection();
        if (conn == null) {
            System.out.println("DB connection failed.");
            return;
        }
        dao.markAllAsRead(conn, Session.loggedInUserEmail);
        MySQLNotification.close(conn);
    }
}
