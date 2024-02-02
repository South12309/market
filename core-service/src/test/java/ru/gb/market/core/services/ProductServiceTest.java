package ru.gb.market.core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.market.api.ProductDto;
import ru.gb.market.core.CoreServiceApplicationTests;
import ru.gb.market.core.entities.Category;
import ru.gb.market.core.entities.Product;
import ru.gb.market.core.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

class ProductServiceTest extends CoreServiceApplicationTests {
    @Autowired
    ProductRepository productRepository;
    @MockBean
    CategoryService categoryService;
    @Autowired
    ProductService productService;


    String title = "productTitle";
    BigDecimal price = new BigDecimal(100);
    String categoryTitle = "Food";
    ProductDto productDto;

    @BeforeEach
    void prepare() {
        productDto = new ProductDto(1L, title, price, categoryTitle);
    }

    @Test
    void findAll() {
    }


    @Test
    void createNewProduct() {
        Category category = new Category();
        category.setId(1L);
        category.setTitle(categoryTitle);
        category.setProducts(Collections.emptyList());
        Mockito.doReturn(Optional.of(category)).when(categoryService).findByTitle(categoryTitle);
        Product product = productService.createNewProduct(productDto);
        Assertions.assertEquals(productDto.getTitle(), product.getTitle());
        Assertions.assertEquals(productDto.getPrice(), product.getPrice());
        Assertions.assertEquals(productDto.getCategoryTitle(), product.getCategory().getTitle());
    }


}