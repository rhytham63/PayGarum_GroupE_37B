/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author User
 */
public class Flightbooking {
    private String departure;
    private String arrival;
    private String date;

    public Flightbooking(String departure, String arrival, String date) {
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
    }

    public String getDeparture() { return departure; }
    public String getArrival() { return arrival; }
    public String getDate() { return date; }
}