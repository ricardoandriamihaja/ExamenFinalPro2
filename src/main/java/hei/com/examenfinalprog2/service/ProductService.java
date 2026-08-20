package hei.com.examenfinalprog2.service;

import hei.com.examenfinalprog2.model.Dto.ProductDto;
import hei.com.examenfinalprog2.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<ProductDto> getProductById(String id) {
        return productRepository.findById(id);
    }
}
