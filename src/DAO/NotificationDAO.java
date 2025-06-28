package DAO;

import Model.Notification;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public List<Notification> getAllNotifications(Connection conn, String email) {
        List<Notification> list = new ArrayList<>();
        String query = "SELECT message, is_read FROM notifications WHERE recipient_email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Notification(rs.getString("message"), rs.getBoolean("is_read")));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching notifications: " + e.getMessage());
        }

        return list;
    }

    public void markAllAsRead(Connection conn, String email) {
        String updateQuery = "UPDATE notifications SET is_read = TRUE WHERE recipient_email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to mark notifications as read: " + e.getMessage());
        }
    }

    public void addNotification(Connection conn, String message, String recipientEmail) {
        String sql = "INSERT INTO notifications (message, is_read, recipient_email) VALUES (?, false, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, message);
            stmt.setString(2, recipientEmail);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error inserting notification: " + e.getMessage());
        }
    }
}
