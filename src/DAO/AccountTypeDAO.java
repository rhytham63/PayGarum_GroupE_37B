/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Database.Database;
import Database.MySqlConnection;
import Model.AccountTypeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountTypeDAO {
    private final Database db = new MySqlConnection(); 

    public void saveAccountType(AccountTypeModel model) {
        String sql = "INSERT INTO account_types (account_type) VALUES (?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = db.openConnection(); 
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, model.getAccountType());

            stmt.executeUpdate();
            System.out.println("Account type saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                db.closeConnection(conn); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
