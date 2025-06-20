/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import DAO.NotificationDAO;
import Database.MySQLNotification;
import Model.Notification;

import java.sql.Connection;
import java.util.List;
/**
 *
 * @author utpre
 */
public class NotificationController {
    public final NotificationDAO dao = new NotificationDAO();

    public String getFormattedNotifications() {
        Connection conn = MySQLNotification.getConnection();
        StringBuilder display = new StringBuilder();

        if (conn == null) return "Unable to connect to database.";

        List<Notification> list = dao.getAllNotifications(conn);
        
        if (list == null) {
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
        dao.markAllAsRead(conn);
        MySQLNotification.close(conn);
    }
    
}
