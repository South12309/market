package ru.gb.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.market.core.entities.Order;
import ru.gb.market.core.entities.OrderItem;
import ru.gb.market.api.ResourceNotFoundException;
import ru.gb.market.core.integrations.CartServiceIntegration;
import ru.gb.market.core.repositories.OrderRepository;
import ru.gb.market.api.CartDto;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartServiceIntegration cartService;
    private final OrderRepository orderRepository;
    private final ProductService productService;


    @Transactional
    public Order createNewOrder(String username,  String address, String phone) {
        CartDto cart = cartService.getCurrentCart(username);//.orElseThrow(() -> new ResourceNotFoundException("Корзина не найдена"));
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Нельзя оформить заказ для пустой корзины");
        }

        Order order = new Order();
        order.setAddress(address);
        order.setPhone(phone);
        order.setTotalPrice(cart.getTotalPrice());
        order.setUsername(username);
        order.setItems(new ArrayList<>());
        cart.getItems().forEach(ci -> {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setPrice(ci.getPrice());
            oi.setQuantity(ci.getQuantity());
            oi.setPricePerProduct(ci.getPricePerProduct());
            oi.setProduct(productService.findById(ci.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
            order.getItems().add(oi);
        });
        cartService.clearCart(username);
        return orderRepository.save(order);
    }

    public List<Order> findUserOrders(String username) {
        return orderRepository.findAllByUsername(username);
    }
}
