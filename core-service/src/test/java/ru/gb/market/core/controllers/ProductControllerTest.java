package ru.gb.market.core.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.market.api.ProductDto;
import ru.gb.market.core.CoreServiceApplicationTests;
import ru.gb.market.core.converters.ProductConverter;
import ru.gb.market.core.services.ProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest extends CoreServiceApplicationTests {
    @Autowired
    ProductService productService;
    @Autowired
    ProductConverter productConverter;
    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAllProducts() {
        Page<ProductDto> products = webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageImpl.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(products.getContent());

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