package hei.com.examenfinalprog2.service;

import hei.com.examenfinalprog2.model.Dto.MovementType;
import hei.com.examenfinalprog2.model.Dto.StockMovementDto;
import hei.com.examenfinalprog2.repository.StockMovementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;

    public List<StockMovementDto> getStockMovements(MovementType type) {
        if (type == null) {
            return stockMovementRepository.findAll();
        }
        return stockMovementRepository.findByType(type);
    }

    public List<StockMovementDto> getStockMovementsByProduct(String productId) {
        return stockMovementRepository.findByProductId(productId);
    }

    public StockMovementDto createStockMovement(StockMovementDto movement) {
        if (movement.getCreatedAt() == null) {
            movement.setCreatedAt(Instant.now());
        }
        return stockMovementRepository.save(movement);
    }

    public Integer getStockForProduct(String productId) {
        return stockMovementRepository.getStockForProduct(productId);
    }
}
