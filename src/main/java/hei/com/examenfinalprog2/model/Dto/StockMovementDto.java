package hei.com.examenfinalprog2.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementDto {
    private String id;
    private String productId;
    private Instant createdAt;
    private MovementType movementType;
    private Integer quantity;
}
