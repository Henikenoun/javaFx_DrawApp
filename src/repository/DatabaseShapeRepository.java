package repository;

import domaine.DatabaseConnection;
import model.ShapeEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseShapeRepository implements ShapeRepository {
    private final Connection connection;

    public DatabaseShapeRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(ShapeEntity shape) {
        save(shape.getType().name(), shape.getDetails());
    }

    @Override
    public void save(String type, String details) {
        if (connection == null) return;
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO shapes (type, details) VALUES (?, ?)")) {
            stmt.setString(1, type);
            stmt.setString(2, details);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
