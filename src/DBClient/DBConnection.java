package DBClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Get database information from the user input
    private static Connection conn = null;

    private static String url = "jdbc:mysql://localhost:3306/employees";
    private static String username = "root";
    private static String password = null;

    public static Connection getConnection() throws SQLException {
        // Connection to the database
        try {
            // Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("connected");
        } catch (java.lang.Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return conn;
    }
}

