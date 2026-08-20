package hei.com.examenfinalprog2.controller;

import hei.com.examenfinalprog2.model.Dto.StockMovementDto;
import hei.com.examenfinalprog2.service.StockMovementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final StockMovementService stockMovementService;

    @GetMapping("/{id}/stock-movements")
    public List<StockMovementDto> getStockMovementsByProduct(@PathVariable String id) {
        return stockMovementService.getStockMovementsByProduct(id);
    }

    @GetMapping("/{id}/stock")
    public Map<String, Object> getStock(@PathVariable String id) {
        Integer stock = stockMovementService.getStockForProduct(id);
        return Map.of("productId", id, "stock", stock);
    }
}
