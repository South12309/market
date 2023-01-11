package ru.gb.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.market.dtos.CartDto;
import ru.gb.market.entities.Order;
import ru.gb.market.entities.OrderItem;
import ru.gb.market.entities.User;
import ru.gb.market.exceptions.ResourceNotFoundException;
import ru.gb.market.repositories.OrderRepository;
import ru.gb.market.utils.Cart;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;

    @Transactional
    public void createNewOrder(String username) {
        Cart cart = cartService.getCurrentCart();
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Нельзя оформить заказ для пустой корзины");
        }
        User user = userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("При оформлении заказа пользователь не найден"));
        Order order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setUser(user);
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
        orderRepository.save(order);
        cartService.clearCart();
    }

    public List<Order> findUserOrders(String username) {

        User user = userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден при попытке найти заказы пользователя"));
        return orderRepository.findAllByUser(user);
    }
}
