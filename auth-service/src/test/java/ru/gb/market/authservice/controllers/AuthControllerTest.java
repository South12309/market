package ru.gb.market.authservice.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.market.api.JwtRequest;
import ru.gb.market.api.JwtResponse;
import ru.gb.market.api.ProductDto;
import ru.gb.market.authservice.AuthServiceApplicationTests;


class AuthControllerTest extends AuthServiceApplicationTests {
    @Autowired
    WebTestClient webTestClient;


    @Test
    void createAuthToken() {

        JwtRequest authRequest = new JwtRequest("bob", "100");
        JwtResponse authResponse = webTestClient.post()
                .uri("/api/v1/auth")
                .bodyValue(authRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtResponse.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(authResponse.getToken());

    }

}