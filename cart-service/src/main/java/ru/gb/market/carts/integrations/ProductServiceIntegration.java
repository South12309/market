package ru.gb.market.carts.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gb.market.api.ProductDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
private final RestTemplate restTemplate;

public Optional<ProductDto> getProduct(Long id) {
    return Optional.ofNullable(restTemplate.getForObject("http://localhost:8189/market/core/api/v1/products/" + id, ProductDto.class));
}
}
