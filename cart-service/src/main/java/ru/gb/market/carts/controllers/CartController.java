package ru.gb.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.api.CartDto;
import ru.gb.market.api.StringResponse;
import ru.gb.market.carts.converters.CartConverter;
import ru.gb.market.carts.services.CartService;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_uuid")
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        if (username!=null) {
            cartService.mergeCarts(username,uuid);
        }
        String targetUuid = getCartUuid(username, uuid);
        return cartConverter.entityToDto(cartService.getCurrentCart(targetUuid));
    }

    @GetMapping("/{uuid}/add/{id}")
    public void addProductToCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.addToCart(targetUuid, id);
    }
    @GetMapping("/{uuid}/clear")
    public void clearCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.clearCart(targetUuid);
    }
    @GetMapping("/{uuid}/remove/{id}")
    public void removeProductFromCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.deleteFromCart(targetUuid, id);
    }
    @GetMapping("/{uuid}/inc/{id}")
    public void increaseProductCountInCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.increaseProductCountInCart(targetUuid, id);
    }
    @GetMapping("/{uuid}/dec/{id}")
    public void decreaseProductCountInCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.decreaseProductCountInCart(targetUuid, id);
    }

    private String getCartUuid(String username, String uuid) {
        if (username != null) {
            return username;
        }
        return uuid;
    }
}
