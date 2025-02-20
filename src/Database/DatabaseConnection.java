package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Use the connection URL from the NetBeans connection
    private static final String URL = "jdbc:mysql://localhost:3306/bakery_db?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Your DB name
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = ""; // Your MySQL password
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Load the MySQL JDBC driver (Connector/J)
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establish connection
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully.");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Connection failed: " + e.getMessage());
            }
        }
        return conn;
    }
}
