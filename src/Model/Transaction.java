*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author User
 */
public class Transaction {
    private String transactionId;
    private String date;
    private double amount;
    private String status;

    public Transaction(String transactionId, String date, double amount, String status) {
        this.transactionId = transactionId;
        this.date = date;
        this.amount = amount;
        this.status = status;
    }

    public String getTransactionId() { return transactionId; }
    public String getDate() { return date; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
}
