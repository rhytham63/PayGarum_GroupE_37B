package DAO;

import java.sql.*;
import java.util.*;
import model.TransactionHistory;

public class TransactionHistoryDAO {
    private final Connection conn;

    public TransactionHistoryDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addTransaction(String email, TransactionHistory txn) throws SQLException {
        // Basic input validation
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (txn == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        String sql = "INSERT INTO transaction_history (type, amount, balance, date, details, user_email) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txn.getType());
            ps.setDouble(2, txn.getAmount());
            ps.setDouble(3, txn.getBalance());
            ps.setString(4, txn.getDate());
            ps.setString(5, txn.getDetails());
            ps.setString(6, email);

            return ps.executeUpdate() > 0;
        }
    }

    public List<TransactionHistory> getUserTransactions(String email) throws SQLException {
        List<TransactionHistory> transactions = new ArrayList<>();
        
        if (email == null || email.trim().isEmpty()) {
            return transactions;
        }

        String sql = "SELECT type, amount, balance, date, details " +
                     "FROM transaction_history " +
                     "WHERE user_email = ? " +
                     "ORDER BY date DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new TransactionHistory(
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getDouble("balance"),
                        rs.getString("date"),
                        rs.getString("details")
                    ));
                }
            }
        }
        return transactions;
    }

    // Optional helper method (remove if not needed)
    private boolean userExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}