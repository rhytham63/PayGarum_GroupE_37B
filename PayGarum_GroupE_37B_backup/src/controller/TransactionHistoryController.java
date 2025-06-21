package controller;

import DAO.TransactionHistoryDAO;
import model.TransactionHistory;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class TransactionHistoryController {
    private final TransactionHistoryDAO transactionHistoryDAO;
    private final String userEmail;

    public TransactionHistoryController(TransactionHistoryDAO transactionHistoryDAO, String userEmail) {
        this.transactionHistoryDAO = transactionHistoryDAO;
        this.userEmail = userEmail;
    }

    public void loadTransactionHistory(JTable transactionTable) {
        try {
            List<TransactionHistory> transactions = transactionHistoryDAO.getUserTransactions(userEmail);
            updateTransactionTable(transactionTable, transactions);
        } catch (SQLException e) {
            showError("Error loading transaction history: " + e.getMessage());
        }
    }

    private void updateTransactionTable(JTable table, List<TransactionHistory> transactions) {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Type", "Amount", "Balance", "Date", "Details"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (TransactionHistory t : transactions) {
            model.addRow(new Object[]{
                t.getType(),
                formatCurrency(t.getAmount()),
                formatCurrency(t.getBalance()),
                t.getDate(),
                t.getDetails()
            });
        }

        table.setModel(model);
        customizeTableAppearance(table);
    }

    private String formatCurrency(double amount) {
        return String.format("₹%.2f", amount);
    }

    private void customizeTableAppearance(JTable table) {
        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(120); // Type
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Amount
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Balance
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Date
        table.getColumnModel().getColumn(4).setPreferredWidth(250); // Details

        // Right-align numeric columns
        table.getColumnModel().getColumn(1).setCellRenderer(new RightAlignRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new RightAlignRenderer());
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Custom cell renderer for right alignment
    private static class RightAlignRenderer extends DefaultTableCellRenderer {
        public RightAlignRenderer() {
            setHorizontalAlignment(SwingConstants.RIGHT);
        }
    }
}