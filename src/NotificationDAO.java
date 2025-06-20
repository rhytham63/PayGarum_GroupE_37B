/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Notification;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author utpre
 */
public class NotificationDAO {
    public List<Notification> getAllNotifications(Connection conn) {
        List<Notification> list = new ArrayList<>();
        return list;
    }

    public void markAllAsRead(Connection conn) {
        // updates is_read = true
    }
}
