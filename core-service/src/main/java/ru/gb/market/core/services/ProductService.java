package ru.gb.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.market.core.dtos.ProductDto;
import ru.gb.market.core.entities.Product;
import ru.gb.market.core.exceptions.ResourceNotFoundException;
import ru.gb.market.core.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория с названием: " + productDto.getCategoryTitle() + " не найдена")));
        productRepository.save(product);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
