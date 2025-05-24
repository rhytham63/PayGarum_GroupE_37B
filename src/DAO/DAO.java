package DAO;

import Database.MySQLConnection;
import View.AccountType;
import java.sql.*;

public class DAO {
    private final MySQLConnection dbConnection;
    
    public DAO() {
        this.dbConnection = new MySQLConnection();
    }
     {
        try {
            Connection conn = MySQLConnection.getConnection();
            String sql = "INSERT INTO account_types (account_type) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            String AccountType = null;
            stmt.setString(1, AccountType);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}