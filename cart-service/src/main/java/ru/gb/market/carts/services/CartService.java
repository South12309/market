package ru.gb.market.carts.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.market.api.ProductDto;
import ru.gb.market.carts.integrations.ProductServiceIntegration;
import ru.gb.market.carts.entities.Cart;
import ru.gb.market.carts.entities.CartItem;
import ru.gb.market.carts.repositories.CartItemRepository;
import ru.gb.market.carts.repositories.CartRepository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private Cart commonCart;

    @PostConstruct
    public void init() {

        commonCart = new Cart();
        commonCart.setItems(new ArrayList<>());
    }

    public Cart getCommontCart() {
        return commonCart;
    }

    public Cart getCartOfUser(String username) {
        Cart cart = cartRepository.findByUsername(username).orElse(cartRepository.save(new Cart(null, username, null, BigDecimal.valueOf(0))));
        return cart;
    }


    public void addToCart(Long productId, String username) {
        Cart cart;
        if (username == null) {
            cart = commonCart;
        } else {
            cart = cartRepository.findByUsername(username).orElse(cartRepository.save(new Cart(null, username, new ArrayList<>(), BigDecimal.valueOf(0))));

        }

//        if (cart.getItems()==null) {
//            cart.setItems(new ArrayList<>());
//        }
        for (CartItem item : cart.getItems()) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                recalculatePriceOfCart(cart);
                return;
            }
        }

        ProductDto p = productServiceIntegration.getProduct(productId);//.orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + productId + " не найден"));
        CartItem cartItem = cartItemRepository.save(new CartItem(null, cart, p.getId(), p.getTitle(), 1, p.getPrice(), p.getPrice()));
        //  CartItem cartItem = new CartItem(null, cart, p.getId(), p.getTitle(), 1, p.getPrice(), p.getPrice());
        cart.getItems().add(cartItem);
        // cart = cartRepository.findByUsername(username).get();
        recalculatePriceOfCart(cart);
        cartRepository.save(cart);

    }

    private void recalculatePriceOfCart(Cart cart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        cart.getItems().forEach(i -> totalPrice.add(i.getPrice()));
        cart.setTotalPrice(totalPrice);
    }

    public void clearCart(String username) {

        Cart cart;
        if (username == null) {
            cart = commonCart;
        } else {
            cart = cartRepository.findByUsername(username).orElse(cartRepository.save(new Cart(null, username, null, BigDecimal.valueOf(0))));
        }
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ONE);
    }

    public void deleteFromCart(Long productId, String username) {
        Cart cart;
        if (username == null) {
            cart = commonCart;
        } else {
            cart = cartRepository.findByUsername(username).orElse(cartRepository.save(new Cart(null, username, null, BigDecimal.valueOf(0))));
        }
        cart.getItems().removeIf(s -> s.getProductId().equals(productId));
        recalculatePriceOfCart(cart);

    }

    public void increaseProductCountInCart(Long productId, String username) {
        Cart cart;
        if (username == null) {
            cart = commonCart;
        } else {
            cart = cartRepository.findByUsername(username).orElse(cartRepository.save(new Cart(null, username, null, BigDecimal.valueOf(0))));
        }
        cart.getItems().stream().filter(s -> s.getProductId().equals(productId)).forEach(m -> {
            m.setQuantity(m.getQuantity() + 1);
            m.setPrice(m.getPrice().add(m.getPricePerProduct()));
        });

    }

    public void decreaseProductCountInCart(Long productId, String username) {
        Cart cart;
        if (username == null) {
            cart = commonCart;
        } else {
            cart = cartRepository.findByUsername(username).orElse(cartRepository.save(new Cart(null, username, null, BigDecimal.valueOf(0))));
        }
        cart.getItems().stream().filter(s -> s.getProductId().equals(productId)).forEach(m -> {
            m.setQuantity(m.getQuantity() + 1);
            m.setPrice(m.getPrice().subtract(m.getPricePerProduct()));
        });

    }
}
