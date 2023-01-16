package ru.gb.market.core.converters;

import org.springframework.stereotype.Component;
import ru.gb.market.core.dtos.CartItemDto;
import ru.gb.market.core.utils.CartItem;


@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem c) {
        return new CartItemDto(c.getProductId(), c.getProductTitle(), c.getQuantity(), c.getPricePerProduct(), c.getPrice());
    }
}
