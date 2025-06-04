package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;

public class TransactionDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/paygarumdb"; // Change to your DB
    private final String jdbcUsername = "root"; // Change accordingly
    private final String jdbcPassword = ""; // Change accordingly

    private static final String SELECT_ALL_TRANSACTIONS = "SELECT * FROM transactions";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TRANSACTIONS);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                String date = rs.getString("date");
                String transactionId = rs.getString("transaction_id");
                double amount = rs.getDouble("amount");
                String status = rs.getString("status");

                transactions.add(new Transaction(date, transactionId, amount, status));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
