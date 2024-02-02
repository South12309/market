package ru.gb.market.carts.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.gb.market.api.ProductDto;
import ru.gb.market.carts.integrations.ProductServiceIntegration;
import ru.gb.market.carts.models.Cart;
import ru.gb.market.carts.models.CartItem;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;
    //    private Map<String, Cart> privateCarts;
//    private Cart commonCart;
    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

//    @PostConstruct
//    public void init() {
//        privateCarts = new HashMap<>();
//        commonCart = new Cart();
//    }

    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }

        Cart o = (Cart) redisTemplate.opsForValue().get(targetUuid);
        return o;
    }

//    public Cart getCartToController(String username) {
//        return getCart(username);
//
//    }

//    public Cart getCartOfUser(String username) {
//        Cart cart = privateCarts.get(username);
//        return cart;
//    }


    public void addToCart(String uuid, Long productId) {
        ProductDto product = productServiceIntegration.getProduct(productId);
        execute(uuid, cart -> cart.add(product));


    }

//    private Cart getCart(String username) {
//        Cart cart;
//        if (username == null) {
//            cart = commonCart;
//        } else {
//            cart = privateCarts.get(username);
//            if(cart==null) {
//                cart = new Cart();
//                privateCarts.put(username, cart);
//            }
//        }
//        return cart;
//    }

//    private void recalculatePriceOfCart(Cart cart) {
//        BigDecimal totalPrice = BigDecimal.ZERO;
//        cart.getItems().forEach(i -> totalPrice.add(i.getPrice()));
//        cart.setTotalPrice(totalPrice);
//    }

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
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }

    public void mergeCarts(String username, String uuid) {
        Cart userCart = getCurrentCart(username);
        Cart commonCart = getCurrentCart(uuid);
        if (commonCart.getItems().size() > 0) {
            commonCart.getItems().stream().forEach(cartItem -> userCart.add(productServiceIntegration.getProduct(cartItem.getProductId())));
            commonCart.clear();//чистим дефолтную
            redisTemplate.opsForValue().set(cartPrefix + username, userCart);
            redisTemplate.opsForValue().set(cartPrefix + uuid, commonCart);
        }
    }
}
