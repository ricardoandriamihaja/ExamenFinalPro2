package hei.com.examenfinalprog2.repository;

import hei.com.examenfinalprog2.model.Dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductRepository {
    private final DatabaseConnection dbConnection;

    public Optional<ProductDto> findById(String id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM product WHERE id = ?")) {
            statement.setString(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSetToProductDto(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching product: " + e.getMessage());
        }
    }

    private ProductDto resultSetToProductDto(ResultSet resultSet) throws SQLException {
        return new ProductDto(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBigDecimal("unit_price")
        );
    }

}
