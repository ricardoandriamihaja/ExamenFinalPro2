package hei.com.examenfinalprog2.repository;

import hei.com.examenfinalprog2.model.Dto.MovementType;
import hei.com.examenfinalprog2.model.Dto.StockMovementDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class StockMovementRepository {

    private final DatabaseConnection dbConnection;

    public List<StockMovementDto> findAll() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            List<StockMovementDto> movements = new ArrayList<>();
            var resultSet = statement.executeQuery("SELECT * FROM stock_movement");
            while (resultSet.next()) {
                movements.add(resultSetToStockMovementDto(resultSet));
            }
            return movements;
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching stock movements: " + e.getMessage());
        }
    }

    public List<StockMovementDto> findByType(MovementType type) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM stock_movement WHERE movement_type = ?")) {
            statement.setString(1, type.name());
            var resultSet = statement.executeQuery();
            List<StockMovementDto> movements = new ArrayList<>();
            while (resultSet.next()) {
                movements.add(resultSetToStockMovementDto(resultSet));
            }
            return movements;
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching stock movements by type: " + e.getMessage());
        }
    }

    public List<StockMovementDto> findByProductId(String productId) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM stock_movement WHERE product_id = ?")) {
            statement.setString(1, productId);
            var resultSet = statement.executeQuery();
            List<StockMovementDto> movements = new ArrayList<>();
            while (resultSet.next()) {
                movements.add(resultSetToStockMovementDto(resultSet));
            }
            return movements;
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching stock movements for product: " + e.getMessage());
        }
    }

    public StockMovementDto save(StockMovementDto movement) {
        if (movement.getId() == null) {
            movement.setId(UUID.randomUUID().toString());
        }
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO stock_movement (id, product_id, created_at, movement_type, quantity) VALUES (?, ?, ?, ?, ?)")) {

            statement.setString(1, movement.getId());
            statement.setString(2, movement.getProductId());
            statement.setTimestamp(3, Timestamp.from(movement.getCreatedAt()));
            statement.setString(4, movement.getMovementType().name());
            statement.setInt(5, movement.getQuantity());
            statement.executeUpdate();

            return movement;
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving stock movement: " + e.getMessage());
        }
    }

    public Integer getStockForProduct(String productId) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT " +
                             "COALESCE(SUM(CASE WHEN movement_type = 'IN' THEN quantity ELSE 0 END), 0) - " +
                             "COALESCE(SUM(CASE WHEN movement_type = 'OUT' THEN quantity ELSE 0 END), 0) AS stock " +
                             "FROM stock_movement WHERE product_id = ?")) {
            statement.setString(1, productId);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("stock");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while computing stock for product: " + e.getMessage());
        }
    }

    private StockMovementDto resultSetToStockMovementDto(ResultSet resultSet) throws SQLException {
        var createdAt = resultSet.getTimestamp("created_at");
        return new StockMovementDto(
                resultSet.getString("id"),
                resultSet.getString("product_id"),
                createdAt != null ? createdAt.toInstant() : null,
                MovementType.valueOf(resultSet.getString("movement_type")),
                resultSet.getInt("quantity")
        );
    }
}
