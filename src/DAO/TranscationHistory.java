/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;
/**
 *
 * @author User
 */
public class TranscationHistory {
     private String url = "jdbc:mysql://localhost:3306/transactiondb";
    private String username = "root";
    private String password = "Sushil@34413";

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transcationhistbl";
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL Connector/J
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Iterate over the result set and populate Transaction objects
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setDate(rs.getString("date"));
                t.setTransactionId(rs.getString("transaction_id"));
                t.setAmount(rs.getDouble("amount"));
                t.setStatus(rs.getString("status"));
                list.add(t);
            }
            // Clean up
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
