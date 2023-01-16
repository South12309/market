package ru.gb.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.market.core.dtos.CartDto;
import ru.gb.market.core.utils.Cart;


import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto entityToDto(Cart c) {
        return new CartDto(c.getItems().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList()), c.getTotalPrice());
    }
}
