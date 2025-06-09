package logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbLogger implements Logger {

    private static final String URL = "jdbc:mysql://localhost:3306/pattern"; // Exemple : base SQLite locale
    private static final String TABLE_NAME = "pattern";

    public DbLogger() {
        createTableIfNotExists();
    }

    @Override
    public void log(String type, String message) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO " + TABLE_NAME + " (type, message) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, type);
            statement.setString(2, message);
            statement.executeUpdate();
            System.out.println("[Database] Log saved: [" + type + "] " + message);
        } catch (SQLException e) {
            System.err.println("[Database] Error logging message: " + e.getMessage());
        }
    }

    private void createTableIfNotExists() {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "type TEXT NOT NULL, "
                    + "message TEXT NOT NULL"
                    + ")";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            System.err.println("[Database] Error creating table: " + e.getMessage());
        }
    }
}
