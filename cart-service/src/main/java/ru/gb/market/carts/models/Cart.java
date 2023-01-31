package ru.gb.market.carts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gb.market.api.ProductDto;


import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class Cart {

    private List<CartItem> items;
    private BigDecimal totalPrice;

    public void add(ProductDto p) {
        for (CartItem item : items) {
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

    public void delete(Long productId) {
        items.removeIf(s -> s.getProductId().equals(productId));
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        items.forEach(i -> totalPrice = totalPrice.add(i.getPrice()));
    }

    public void increaseProductCountInCart(Long productId) {
        items.stream().filter(s->s.getProductId().equals(productId)).forEach(m->m.incrementQuantity());
    }
    public void decreaseProductCountInCart(Long productId) {
        items.stream().filter(s->s.getProductId().equals(productId)).forEach(m->m.decrementQuantity());
    }
}
