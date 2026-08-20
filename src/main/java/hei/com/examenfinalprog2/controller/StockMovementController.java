package hei.com.examenfinalprog2.controller;

import hei.com.examenfinalprog2.model.Dto.MovementType;
import hei.com.examenfinalprog2.model.Dto.StockMovementDto;
import hei.com.examenfinalprog2.service.StockMovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock-movements")
@AllArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    // GET /stock-movements?type=in|out
    @GetMapping
    public List<StockMovementDto> getStockMovements(@RequestParam(required = false) String type) {
        MovementType movementType = (type != null) ? MovementType.valueOf(type.toUpperCase()) : null;
        return stockMovementService.getStockMovements(movementType);
    }

    // POST /stock-movements
    @PostMapping
    public ResponseEntity<StockMovementDto> createStockMovement(@RequestBody StockMovementDto movement) {
        return new ResponseEntity<>(stockMovementService.createStockMovement(movement), HttpStatus.CREATED);
    }
}
