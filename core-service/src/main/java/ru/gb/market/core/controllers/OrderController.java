package ru.gb.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.core.converters.OrderConverter;
import ru.gb.market.api.OrderDto;
import ru.gb.market.core.services.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @GetMapping
    public List<OrderDto> getUserOrders(Principal principal) {
        return orderService.findUserOrders(principal.getName()).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping("/{address}/{phone}")
    public void createNewOrder(Principal principal, @PathVariable String address, @PathVariable String phone) {
        orderService.createNewOrder(principal.getName(), address, phone);
    }
}
