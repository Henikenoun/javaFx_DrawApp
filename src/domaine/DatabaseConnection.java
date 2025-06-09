package domaine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 👉 obligatoire pour enregistrer le driver
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pattern", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
