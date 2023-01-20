package ru.gb.market.core.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.market.api.ProductDto;
import ru.gb.market.api.ResourceNotFoundException;
import ru.gb.market.core.CoreServiceApplicationTests;
import ru.gb.market.core.entities.Product;
import ru.gb.market.core.repositories.ProductRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest extends CoreServiceApplicationTests {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
            ProductService productService;

    String title="productTitle";
    BigDecimal price=new BigDecimal(100);

    @BeforeEach
    void prepare() {
        ProductDto productDto= new ProductDto(1L,title, price, "Food");
    }

    @Test

    void findAll() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void createNewProduct() {

        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
      //  product.setCategory(categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория с названием: " + productDto.getCategoryTitle() + " не найдена")));
        productRepository.save(product);
    }

    @Test
    void findById() {
    }
}