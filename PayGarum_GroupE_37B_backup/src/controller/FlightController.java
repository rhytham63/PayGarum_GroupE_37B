/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import DAO.FlightDAO;
import Model.Flightbooking;
import java.sql.Connection;

/**
 *
 * @author User
 */
public class FlightController {
    
    private FlightDAO flightDAO;

public FlightController(Connection connection) {
    this.flightDAO = new FlightDAO(connection);
}

    public void processSelection(String departure, String arrival, String date) {
        Flightbooking flight = new Flightbooking(departure, arrival, date);
        boolean success = flightDAO.saveSelection(flight);

        if (success) {
            System.out.println("Flight selection saved successfully!");
        } else {
            System.out.println("Failed to save selection.");
        }
    }
}
