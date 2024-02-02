package ru.gb.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.core.converters.OrderConverter;
import ru.gb.market.api.OrderDto;
import ru.gb.market.core.entities.Order;
import ru.gb.market.core.services.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader String username) {
        return orderService.findUserOrders(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping("/{address}/{phone}")
    public OrderDto createNewOrder(@RequestHeader(name = "username", required = true) String username, @PathVariable String address, @PathVariable String phone) {
        return orderConverter.entityToDto(orderService.createNewOrder(username, address, phone));
    }


}
