package ru.gb.market.converters;

import org.springframework.stereotype.Component;
import ru.gb.market.dtos.OrderItemDto;
import ru.gb.market.entities.OrderItem;


@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem o) {
        return new OrderItemDto(o.getProduct().getId(), o.getProduct().getTitle(), o.getQuantity(), o.getPricePerProduct(), o.getPrice());
    }
}
