package ru.gb.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.core.converters.CartConverter;
import ru.gb.market.core.dtos.CartDto;
import ru.gb.market.core.services.CartService;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.entityToDto(cartService.getCurrentCart());
    }

    @GetMapping("/add/{productId}")
    public void addProductToCart(@PathVariable Long productId) {
        cartService.addToCart(productId);
    }
    @GetMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }
    @GetMapping("/delete/{productId}")
    public void deleteProductFromCart(@PathVariable Long productId) {
        cartService.deleteFromCart(productId);
    }
    @GetMapping("/inc/{productId}")
    public void increaseProductCountInCart(@PathVariable Long productId) {
        cartService.increaseProductCountInCart(productId);
    }
    @GetMapping("/dec/{productId}")
    public void decreaseProductCountInCart(@PathVariable Long productId) {
        cartService.decreaseProductCountInCart(productId);
    }
}
