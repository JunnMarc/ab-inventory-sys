package DAO;

import Database.DatabaseConnection;
import java.sql.*;

public class CategoryDAO {
    private Connection conn;
    private PreparedStatement prepStatement;

    public CategoryDAO() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean addCategory(String categoryName) {
       String sql = "INSERT INTO Category (category_name) VALUES (?)";
       try {
           prepStatement = conn.prepareStatement(sql);
           prepStatement.setString(1, categoryName);

           return prepStatement.executeUpdate() > 0; 
       } catch (SQLException e) {
           e.printStackTrace();
           return false;  
       } finally {
           try {
               if (prepStatement != null) prepStatement.close();  
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }


    // Update an existing category
    public boolean updateCategory(int categoryId, String categoryName) {
        try {
            String sql = "UPDATE Category SET category_name = ? WHERE category_id = ?";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setString(1, categoryName);
            prepStatement.setInt(2, categoryId);

            return prepStatement.executeUpdate() > 0; 
        } catch (SQLException e) {
            System.out.println("Error updating category: " + e.getMessage());
            return false;
        }
    }

    // Delete a category by ID
    public boolean deleteCategory(int categoryId) {
        try {
            String sql = "DELETE FROM Category WHERE category_id = ?";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setInt(1, categoryId);

            return prepStatement.executeUpdate() > 0; 
        } catch (SQLException e) {
            System.out.println("Error deleting category: " + e.getMessage());
            return false;
        }
    }

    // Get all categories
    public ResultSet getCategoryResultSet() {
        try {
            String sql = "SELECT category_id, category_name FROM Category";
            prepStatement = conn.prepareStatement(sql); 
            return prepStatement.executeQuery();  
        } catch (SQLException e) {
            System.out.println("Error fetching categories: " + e.getMessage());
            return null;
        }
    }
}