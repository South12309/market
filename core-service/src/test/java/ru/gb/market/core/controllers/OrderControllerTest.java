package ru.gb.market.core.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.gb.market.api.OrderDto;
import ru.gb.market.api.ProductDto;
import ru.gb.market.core.CoreServiceApplicationTests;
import ru.gb.market.core.converters.OrderConverter;
import ru.gb.market.core.entities.Order;
import ru.gb.market.core.services.OrderService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


class OrderControllerTest extends CoreServiceApplicationTests {
    @MockBean
    OrderService orderService;
    @Autowired
    OrderConverter orderConverter;
    @Autowired
    WebTestClient webTestClient;

    String username = "user1";
    String address = "TestAddress";
    String phone="TestPhone";

    @BeforeEach
    void before() {
        Order order = new Order();
        order.setUsername(username);
        order.setAddress(address);
        order.setPhone(phone);
        order.setItems(Collections.emptyList());
        Mockito.doReturn(List.of(order)).when(orderService).findUserOrders(username);
        Mockito.doReturn(order).when(orderService).createNewOrder(username, address, phone);
    }

    @Test
    void getUserOrders() {

        webTestClient.get()
                .uri("/api/v1/orders")
                .header("username", username)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$")
                .isNotEmpty()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].address").isEqualTo(address)
                .jsonPath("$[0].phone").isEqualTo(phone);
    }

    @Test
    void createNewOrder() {
        OrderDto orderFromHttp = webTestClient.post()
                .uri("/api/v1/orders/" + address + "/" +phone)
                .header("username", username)
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(orderFromHttp);
        Assertions.assertEquals(orderFromHttp.getAddress(),address);
        Assertions.assertEquals(orderFromHttp.getPhone(),phone);
    }




    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader String username) {
        return orderService.findUserOrders(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping("/{address}/{phone}")
    public Order createNewOrder(@RequestHeader String username, @PathVariable String address, @PathVariable String phone) {
        return orderService.createNewOrder(username, address, phone);
    }
}