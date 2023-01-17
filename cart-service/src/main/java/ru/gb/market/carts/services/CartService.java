package ru.gb.market.carts.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.market.api.ProductDto;
import ru.gb.market.api.ResourceNotFoundException;
import ru.gb.market.carts.integrations.ProductServiceIntegration;
import ru.gb.market.carts.models.Cart;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setItems(new ArrayList<>());
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addToCart(Long productId) {
        ProductDto p = productServiceIntegration.getProduct(productId);//.orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + productId + " не найден"));
        cart.add(p);
    }

    public void clearCart() {
        cart.clear();
    }

    public void deleteFromCart(Long productId) {
        cart.delete(productId);
    }

    public void increaseProductCountInCart(Long productId) {
        cart.increaseProductCountInCart(productId);
    }

    public void decreaseProductCountInCart(Long productId) {
        cart.decreaseProductCountInCart(productId);
    }
}
