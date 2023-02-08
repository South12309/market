package ru.gb.market.carts.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Map<String, Cart> privateCarts;
    private Cart commonCart;

    @PostConstruct
    public void init() {
        privateCarts = new HashMap<>();
        commonCart = new Cart(new ArrayList<CartItem>(), BigDecimal.ZERO);
    }

    public Cart getCartToController(String username) {
        return getCart(username);

    }

    public Cart getCartOfUser(String username) {
        Cart cart = privateCarts.get(username);
        return cart;
    }


    public void addToCart(Long productId, String username) {
        Cart cart = getCart(username);
        ProductDto p = productServiceIntegration.getProduct(productId);
        cart.add(p);


    }

    private Cart getCart(String username) {
        Cart cart;
        if (username == null) {
            cart = commonCart;
        } else {
            cart = privateCarts.get(username);
            if(cart==null) {
                cart = new Cart(new ArrayList<CartItem>(), BigDecimal.valueOf(0));
                privateCarts.put(username, cart);
            }
        }
        return cart;
    }

//    private void recalculatePriceOfCart(Cart cart) {
//        BigDecimal totalPrice = BigDecimal.ZERO;
//        cart.getItems().forEach(i -> totalPrice.add(i.getPrice()));
//        cart.setTotalPrice(totalPrice);
//    }

    public void clearCart(String username) {
        Cart cart = getCart(username);
        cart.clear();
    }

    public void deleteFromCart(Long productId, String username) {
        Cart cart = privateCarts.get(username);
        cart.delete(productId);

    }

    public void increaseProductCountInCart(Long productId, String username) {
        Cart cart = privateCarts.get(username);
        cart.increaseProductCountInCart(productId);

    }

    public void decreaseProductCountInCart(Long productId, String username) {
        Cart cart = privateCarts.get(username);
        cart.decreaseProductCountInCart(productId);
    }
}
