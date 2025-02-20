/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Database.DatabaseConnection;

import java.sql.*;

// Data Access Object for Products
public class ProductDAO {
    Connection conn = null;
    PreparedStatement prepStatement = null;
    PreparedStatement prepStatement2 = null;
    Statement statement = null;
    
    public ProductDAO() {
        try {
            conn = DatabaseConnection.getConnection();
            statement = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet getProductResultSet() {
        try {
            String sql = "SELECT * FROM Product";
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error fetching inventory: " + e.getMessage());
            return null;
        }
    }
    
    public boolean addProduct(String name, String type) {
        try {
            String sql = "INSERT INTO Product (product_name, product_type) VALUES (?, ?)";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setString(1, name);
            prepStatement.setString(2, type);
            return prepStatement.executeUpdate() > 0; // Returns true if insertion is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateProduct(int id, String name, String type) {
        try {
            String sql = "UPDATE Product SET product_name = ?, product_type = ? WHERE product_id = ?";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setString(1, name);
            prepStatement.setString(2, type);
            prepStatement.setInt(3, id);

            int rowsUpdated = prepStatement.executeUpdate();
            return rowsUpdated > 0; // Returns true if the update was successful
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteProduct(int id) {
        try {
            String sql = "DELETE FROM Product WHERE product_id = ?";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setInt(1, id);
            return prepStatement.executeUpdate() > 0; // Returns true if deletion is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
