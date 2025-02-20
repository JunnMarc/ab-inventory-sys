/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Database.DatabaseConnection;
import java.sql.*;

public class InventoryDAO {
    private Connection conn;
    private PreparedStatement prepStatement;
    private Statement statement;

    public InventoryDAO() {
        try {
            conn = DatabaseConnection.getConnection();
            statement = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Stack In: Add stock to the inventory
    public void stackIn(int productId, int quantity) {
        try {
            String sql = "UPDATE Inventory SET total_stock = total_stock + ? WHERE product_id = ?";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setInt(1, quantity);
            prepStatement.setInt(2, productId);
            int rowsAffected = prepStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Stock added successfully!");
            } else {
                System.out.println("Product not found! Adding new entry.");
                String insertSQL = "INSERT INTO Inventory (product_id, total_stock) VALUES (?, ?)";
                prepStatement = conn.prepareStatement(insertSQL);
                prepStatement.setInt(1, productId);
                prepStatement.setInt(2, quantity);
                prepStatement.executeUpdate();
                System.out.println("New stock entry created.");
            }
        } catch (SQLException e) {
            System.out.println("Error in Stack In: " + e.getMessage());
        }
    }

    // Stack Out: Remove stock from the inventory
    public void stackOut(int productId, int quantity) {
        try {
            String checkSQL = "SELECT total_stock FROM Inventory WHERE product_id = ?";
            prepStatement = conn.prepareStatement(checkSQL);
            prepStatement.setInt(1, productId);
            ResultSet rs = prepStatement.executeQuery();

            if (rs.next()) {
                int currentStock = rs.getInt("total_stock");
                if (currentStock >= quantity) {
                    String sql = "UPDATE Inventory SET total_stock = total_stock - ? WHERE product_id = ?";
                    prepStatement = conn.prepareStatement(sql);
                    prepStatement.setInt(1, quantity);
                    prepStatement.setInt(2, productId);
                    prepStatement.executeUpdate();
                    System.out.println("Stock removed successfully!");
                } else {
                    System.out.println("Insufficient stock! Available: " + currentStock);
                }
            } else {
                System.out.println("Product not found in inventory.");
            }
        } catch (SQLException e) {
            System.out.println("Error in Stack Out: " + e.getMessage());
        }
    }
    public ResultSet getInventoryResultSet() {
        try {
            String sql = "SELECT * FROM Inventory";
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error fetching inventory: " + e.getMessage());
            return null;
        }
    }
    // View current inventory
    public void viewInventory() {
        try {
            String sql = "SELECT * FROM Inventory";
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("Inventory List:");
            while (rs.next()) {
                System.out.println("Product ID: " + rs.getInt("product_id") + 
                                   " | Stock: " + rs.getInt("total_stock"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching inventory: " + e.getMessage());
        }
    }
}