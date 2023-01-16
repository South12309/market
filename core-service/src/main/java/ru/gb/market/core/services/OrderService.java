package ru.gb.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.market.core.entities.Order;
import ru.gb.market.core.entities.OrderItem;
import ru.gb.market.core.entities.User;
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
    private final UserService userService;

    @Transactional
    public void createNewOrder(String username, String address, String phone) {
        CartDto cart = cartService.getCurrentCart().orElseThrow(() -> new ResourceNotFoundException("Корзина не найдена"));
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Нельзя оформить заказ для пустой корзины");
        }
        User user = userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("При оформлении заказа пользователь не найден"));
        Order order = new Order();
        order.setAddress(address);
        order.setPhone(phone);
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
