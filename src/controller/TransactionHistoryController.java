package controller;

import DAO.TransactionHistoryDAO;
import Database.MySqlConnection;
import Model.TransactionHistory;
import View.Dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransactionHistoryController {
    private final TransactionHistoryDAO transactionHistoryDAO;
    private final String userEmail;
    private final Dashboard dashboard;

    public TransactionHistoryController(Dashboard dashboard, String userEmail) {
        this.dashboard = dashboard;
        this.userEmail = userEmail;
        Connection conn = new MySqlConnection().openConnection();
        this.transactionHistoryDAO = new TransactionHistoryDAO(conn);
        initializeListener();
    }

    private void initializeListener() {
        dashboard.getRefreshHistoryButton().addActionListener(e -> {
            loadTransactionHistory(dashboard.getTransactionTable());
        });
    }

    public void loadTransactionHistory(JTable transactionTable) {
        try {
            List<TransactionHistory> transactions = transactionHistoryDAO.getUserTransactions(userEmail);
            updateTransactionTable(transactionTable, transactions);
        } catch (SQLException e) {
            showError("Error loading transaction history: " + e.getMessage());
        }
    }

    public void loadFilteredTransactions(JTable transactionTable, String filterType) {
        try {
            List<TransactionHistory> transactions;

            if (filterType.equalsIgnoreCase("All")) {
                transactions = transactionHistoryDAO.getUserTransactions(userEmail);
            } else {
                transactions = transactionHistoryDAO.getUserTransactionsByType(userEmail, filterType);
            }

            updateTransactionTable(transactionTable, transactions);
        } catch (SQLException e) {
            showError("Error loading filtered transactions: " + e.getMessage());
        }
    }

    private void updateTransactionTable(JTable table, List<TransactionHistory> transactions) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Type", "Amount", "Balance", "Date", "Details"}, 0) {
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
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(250);

        table.getColumnModel().getColumn(1).setCellRenderer(new RightAlignRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new RightAlignRenderer());
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static class RightAlignRenderer extends DefaultTableCellRenderer {
        public RightAlignRenderer() {
            setHorizontalAlignment(SwingConstants.RIGHT);
        }
    }
}
