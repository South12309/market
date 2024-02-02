package ru.gb.market.carts.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import ru.gb.market.api.ProductDto;
import ru.gb.market.carts.aop.RedisTemplateAdapter;
import ru.gb.market.carts.integrations.ProductServiceIntegration;
import ru.gb.market.carts.models.Cart;

import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplateAdapter<String, Object> redisTemplateAdapter;



    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;


    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!redisTemplateAdapter.hasKey(targetUuid)) {
            redisTemplateAdapter.set(targetUuid, new Cart());
        }

        Cart o = (Cart) redisTemplateAdapter.get(targetUuid);
        return o;
    }



    public void addToCart(String uuid, Long productId) {
        ProductDto product = productServiceIntegration.getProduct(productId);
        execute(uuid, cart -> cart.add(product));


    }



    public void clearCart(String uuid) {
        execute(uuid, Cart::clear);
    }

    public void deleteFromCart(String uuid, Long productId) {
        execute(uuid, cart -> cart.delete(productId));

    }

    public void increaseProductCountInCart(String uuid, Long productId) {
        execute(uuid, cart -> cart.increaseProductCountInCart(productId));


    }

    public void decreaseProductCountInCart(String uuid, Long productId) {
        execute(uuid, cart -> cart.decreaseProductCountInCart(productId));

    }

    private void execute(String uuid, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(uuid);
        operation.accept(cart);
        saveCartToRedis(cartPrefix + uuid, cart);
    }

    public void mergeCarts(String username, String uuid) {
        Cart userCart = getCurrentCart(username);
        Cart commonCart = getCurrentCart(uuid);
        if (commonCart.getItems().size() > 0) {
            commonCart.getItems().stream().forEach(cartItem -> userCart.add(productServiceIntegration.getProduct(cartItem.getProductId())));
            commonCart.clear();
            saveCartToRedis(cartPrefix + username, userCart);
            saveCartToRedis(cartPrefix + uuid, commonCart);
        }
    }

    public void saveCartToRedis(String uuid, Cart cart) {
        redisTemplateAdapter.set(uuid, cart);
    }
}
