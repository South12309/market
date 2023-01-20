package ru.gb.market.core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.market.api.CartDto;
import ru.gb.market.api.CartItemDto;
import ru.gb.market.api.ProductDto;
import ru.gb.market.core.CoreServiceApplicationTests;
import ru.gb.market.core.converters.ProductConverter;
import ru.gb.market.core.entities.Order;
import ru.gb.market.core.entities.Product;
import ru.gb.market.core.integrations.CartServiceIntegration;
import ru.gb.market.core.repositories.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest extends CoreServiceApplicationTests {

    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductConverter productConverter;
    @MockBean
    CartServiceIntegration cartService;

    CartDto returnCart;
    String username = "testUsername";
    String address = "testAddress";
    String phone = "testPhone";


    @BeforeEach
    void fillCart() {
        orderService = new OrderService(cartService, orderRepository, productService);
        BigDecimal totalPrice=BigDecimal.ONE;
        List<CartItemDto> cartItemList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            System.out.println(productService.findById(Long.valueOf(i)).get().getTitle());
            ProductDto productDto = productService.findById(Long.valueOf(i)).map(productConverter::entityToDto).get();
            BigDecimal totalPricePerItem = productDto.getPrice().multiply(BigDecimal.valueOf(i));
            cartItemList.add(new CartItemDto(productDto.getId(), productDto.getTitle(), i, productDto.getPrice(), totalPricePerItem));
            totalPrice.multiply(totalPricePerItem);
        }
        returnCart = new CartDto(cartItemList, totalPrice);
    }

    @Test
    void createNewOrder() {
     //   orderService = new OrderService(cartService, orderRepository, productService);
        Mockito.when(cartService.getCurrentCart()).thenReturn(returnCart);

        Order savedOrder = orderService.createNewOrder(username, address, phone);
        Assertions.assertNotNull(savedOrder);
        Assertions.assertNotNull(savedOrder.getItems());
        Assertions.assertEquals(username, savedOrder.getUsername());
        Assertions.assertEquals(address, savedOrder.getAddress());
        Assertions.assertEquals(phone, savedOrder.getPhone());

    }

    @Test
    void findUserOrders() {
        Order gottenOrderFromDB = orderService.findUserOrders(username).stream().findFirst().get();
        Assertions.assertNotNull(gottenOrderFromDB);
        Assertions.assertNotNull(gottenOrderFromDB.getItems());
        Assertions.assertEquals(username, gottenOrderFromDB.getUsername());
        Assertions.assertEquals(address, gottenOrderFromDB.getAddress());
        Assertions.assertEquals(phone, gottenOrderFromDB.getPhone());
    }
}