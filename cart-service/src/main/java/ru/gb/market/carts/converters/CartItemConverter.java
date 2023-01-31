package ru.gb.market.carts.converters;

import org.springframework.stereotype.Component;
import ru.gb.market.api.CartItemDto;
import ru.gb.market.carts.models.CartItem;


@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem c) {
        return new CartItemDto(c.getProductId(), c.getProductTitle(), c.getQuantity(), c.getPricePerProduct(), c.getPrice());
    }
}
