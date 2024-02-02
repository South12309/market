package ru.gb.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.market.api.OrderDto;
import ru.gb.market.core.entities.Order;


import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto(Order o) {
        return new OrderDto(o.getId(), o.getItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()), o.getTotalPrice(), o.getAddress(), o.getPhone());
    }
}
