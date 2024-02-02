package ru.gb.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import ru.gb.market.api.CartDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    public Optional<CartDto> getCurrentCart() {
        return Optional.ofNullable(restTemplate.getForObject("http://localhost:8190/market/cart/api/v1/cart", CartDto.class));
    }

    public void addProductToCart(Long id) {
        restTemplate.getForObject("http://localhost:8190/market/cart/api/v1/cart/add/" + id, CartDto.class);
    }

    public void clearCart() {
        restTemplate.getForObject("http://localhost:8190/market/cart/api/v1/cart/clear", Optional.class);
    }

    public void deleteProductFromCart(Long id) {
        restTemplate.getForObject("http://localhost:8190/market/cart/api/v1/cart/delete/" + id, Optional.class);
    }

    public void increaseProductCountInCart(Long id) {
        restTemplate.getForObject("http://localhost:8190/market/cart/api/v1/cart/inc/" + id, Optional.class);
    }

    public void decreaseProductCountInCart(Long id) {
        restTemplate.getForObject("http://localhost:8190/market/cart/api/v1/cart/dec/" + id, Optional.class);
    }


}
