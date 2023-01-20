package ru.gb.market.core.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.market.core.CoreServiceApplicationTests;
import ru.gb.market.core.converters.ProductConverter;
import ru.gb.market.core.services.ProductService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductControllerTest extends CoreServiceApplicationTests {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductConverter productConverter;
    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void getProductById() {
    }

    @Test
    void createNewProducts() {
    }

    @Test
    void deleteProductById() {
    }
}