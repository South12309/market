package ru.gb.market.core.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.market.api.ProductDto;
import ru.gb.market.core.CoreServiceApplicationTests;
import ru.gb.market.core.converters.ProductConverter;
import ru.gb.market.core.entities.Product;
import ru.gb.market.core.repositories.CategoryRepository;
import ru.gb.market.core.repositories.ProductRepository;

import java.math.BigDecimal;

class ProductControllerTest extends CoreServiceApplicationTests {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductConverter productConverter;
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAllProducts() {
        webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isNotEmpty()
                .jsonPath("$.content.length()").isEqualTo(5)
                .jsonPath("$.content[0].title").isEqualTo(productRepository.findById(1L).get().getTitle())
                .jsonPath("$.content[0].categoryTitle").isEqualTo(productRepository.findById(1L).get().getCategory().getTitle());


    }

    @Test
    void createNewProducts() {
        ProductDto productDto = new ProductDto();
        productDto.setTitle("TestProduct");
        productDto.setPrice(BigDecimal.TEN);
        productDto.setCategoryTitle(categoryRepository.findById(1L).orElse(null).getTitle());
        ProductDto productByHttp = webTestClient.post()
                .uri("/api/v1/products")
                .bodyValue(productDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductDto.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertEquals(productDto.getTitle(), productByHttp.getTitle());
        Product productFromDB = productRepository.findById(productByHttp.getId()).get();
        Assertions.assertEquals(productFromDB.getTitle(), productByHttp.getTitle());

    }

    @Test
    void getProductById() {
        Product product = new Product();
        product.setTitle("TestProduct");
        product.setPrice(BigDecimal.TEN);
        product.setCategory(categoryRepository.findById(1L).orElse(null));
        ProductDto productDto = productConverter.entityToDto(productRepository.save(product));
        ProductDto productByHttp = webTestClient.get()
                .uri("/api/v1/products/" + product.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(productDto.getId(), productByHttp.getId());
        Assertions.assertEquals(productDto.getTitle(), productByHttp.getTitle());

    }


    @Test
    void deleteProductById() {
        ProductDto productDto = new ProductDto();
        productDto.setTitle("TestProduct");
        productDto.setPrice(BigDecimal.TEN);
        productDto.setCategoryTitle(categoryRepository.findById(1L).orElse(null).getTitle());
        ProductDto productByHttp = webTestClient.post()
                .uri("/api/v1/products", productDto)
                .bodyValue(productDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductDto.class)
                .returnResult()
                .getResponseBody();
        webTestClient.delete()
                .uri("/api/v1/products/" + productByHttp.getId())
                .exchange()
                .expectStatus().isOk();
        Assertions.assertNull(productRepository.findById(productByHttp.getId()).orElse(null));


    }
}