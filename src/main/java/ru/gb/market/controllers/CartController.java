package ru.gb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.converters.CartConverter;
import ru.gb.market.dtos.CartDto;
import ru.gb.market.services.CartService;


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
    @PostMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }
    @GetMapping("/delete/{productId}")
    public void deleteProductFromCart(@PathVariable Long productId) {
        cartService.deleteFromCart(productId);
    }
}
