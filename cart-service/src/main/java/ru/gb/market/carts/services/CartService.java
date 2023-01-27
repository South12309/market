package ru.gb.market.carts.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.market.api.ProductDto;
import ru.gb.market.carts.integrations.ProductServiceIntegration;
import ru.gb.market.carts.entities.Cart;
import ru.gb.market.carts.entities.CartItem;
import ru.gb.market.carts.repositories.CartItemRepository;
import ru.gb.market.carts.repositories.CartRepository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setItems(new ArrayList<>());
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addToCart(String username, Long productId) {
        ProductDto p = productServiceIntegration.getProduct(productId);//.orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + productId + " не найден"));
        Cart cart = cartRepository.findByUsername(username).orElse(cartRepository.save(new Cart(null, username, null, BigDecimal.valueOf(0))));

        for (CartItem item : cart.getItems()) {
            if (item.getProductId().equals(p.getId())) {
                item.incrementQuantity();
                recalculate();
                return;
            }
        }
        CartItem cartItem = new CartItem(p.getId(), p.getTitle(), 1, p.getPrice(), p.getPrice());
        items.add(cartItem);
        recalculate();

    }
    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        items.forEach(i -> totalPrice = totalPrice.add(i.getPrice()));
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
