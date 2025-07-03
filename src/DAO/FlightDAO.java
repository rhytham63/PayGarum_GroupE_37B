/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Flightbooking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Database.*;
/**
 *
 * @author User
 */
public class FlightDAO {
    private final MySqlConnection dbConnection = new MySqlConnection();

    public boolean saveSelection(Flightbooking flight, String userEmail) {
        String sql = "INSERT INTO flight_selections (user_email, departure, arrival, date) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbConnection.openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            stmt.setString(2, flight.getDeparture());
            stmt.setString(3, flight.getArrival());
            stmt.setString(4, flight.getDate());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
