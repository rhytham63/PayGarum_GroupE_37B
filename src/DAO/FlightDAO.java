/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Flightbooking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author User
 */
public class FlightDAO {    

    
    private Connection connection;

    public FlightDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean saveSelection(Flightbooking flight) {
        String sql = "INSERT INTO flight_selections (departure, arrival, date) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, flight.getDeparture());
            stmt.setString(2, flight.getArrival());
            stmt.setString(3, flight.getDate());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

