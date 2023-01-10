package ru.gb.market.converters;

import org.springframework.stereotype.Component;
import ru.gb.market.dtos.CartItemDto;
import ru.gb.market.utils.CartItem;


@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem c) {
        return new CartItemDto(c.getProductId(), c.getProductTitle(), c.getQuantity(), c.getPricePerProduct(), c.getPrice());
    }
}
