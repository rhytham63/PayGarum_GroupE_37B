package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionHistory {
    private String type;
    private double amount;
    private double balance;
    private String date;
    private String details;

    public TransactionHistory(String type, double amount, double balance, String date, String details) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.date = date;
        this.details = details;
    }

    // Getters
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalance() { return balance; }
    public String getDate() { return date; }
    public String getDetails() { return details; }

    // Helper method for current timestamp
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}