package ru.gb.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.api.CartDto;
import ru.gb.market.carts.converters.CartConverter;
import ru.gb.market.carts.services.CartService;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getCurrentCart(@RequestHeader(required = false) String username) {
        return cartConverter.entityToDto(cartService.getCartToController(username));
    }

    @GetMapping("/add/{productId}")
    public void addProductToCart(@PathVariable Long productId, @RequestHeader(required = false) String username) {
        cartService.addToCart(productId, username);
    }
    @GetMapping("/clear")
    public void clearCart(@RequestHeader(required = false) String username) {
        cartService.clearCart(username);
    }
    @GetMapping("/delete/{productId}")
    public void deleteProductFromCart(@PathVariable Long productId, @RequestHeader(required = false) String username) {
        cartService.deleteFromCart(productId,username);
    }
    @GetMapping("/inc/{productId}")
    public void increaseProductCountInCart(@PathVariable Long productId, @RequestHeader(required = false) String username) {
        cartService.increaseProductCountInCart(productId, username);
    }
    @GetMapping("/dec/{productId}")
    public void decreaseProductCountInCart(@PathVariable Long productId, @RequestHeader(required = false) String username) {
        cartService.decreaseProductCountInCart(productId, username);
    }
}
