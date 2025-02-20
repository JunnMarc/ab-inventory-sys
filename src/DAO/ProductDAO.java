package DAO;

import Database.DatabaseConnection;
import java.sql.*;

public class ProductDAO {
    private Connection conn;
    private PreparedStatement prepStatement;
    private Statement statement;
    
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
            String sql = "SELECT p.product_id, p.product_name, c.category_name " + 
                         "FROM Product p " + 
                         "JOIN Category c ON p.product_category = c.category_id"; // Fetch category name instead of ID
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
            return null;
        }
    }


   // Get all categories
   public ResultSet getCategoryResultSet() {
       try {
           String sql = "SELECT category_id, category_name FROM Category";
           return statement.executeQuery(sql);
       } catch (SQLException e) {
           System.out.println("Error fetching categories: " + e.getMessage());
           return null;
       }
   }
    
   
    public boolean addProduct(String name, int categoryId) {
        try {
            String sql = "INSERT INTO Product (product_name, product_category) VALUES (?, ?)";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setString(1, name);
            prepStatement.setInt(2, categoryId); 

            return prepStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
   
    public boolean updateProduct(int id, String name, int categoryId) {
        try {
            String sql = "UPDATE Product SET product_name = ?, product_category = ? WHERE product_id = ?";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setString(1, name);
            prepStatement.setInt(2, categoryId);  
            prepStatement.setInt(3, id);

            return prepStatement.executeUpdate() > 0; 
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
            return false;
        }
    }
    
    // Delete a product by ID
    public boolean deleteProduct(int id) {
        try {
            String sql = "DELETE FROM Product WHERE product_id = ?";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setInt(1, id);

            return prepStatement.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public ResultSet getCategories() {
        try {
            String sql = "SELECT * FROM Category";
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error fetching categories: " + e.getMessage());
            return null;
        }
    }
}
