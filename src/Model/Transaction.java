package model;

public class Transaction {
    private String date;
    private String transactionId;
    private double amount;
    private String status;

    public Transaction() { }
    public Transaction(String date, String transactionId, double amount, String status) {
        this.date = date;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
    }

    public String getDate() { return date; }
    public String getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }

    public void setDate(String date) { this.date = date; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setStatus(String status) { this.status = status; }
}
