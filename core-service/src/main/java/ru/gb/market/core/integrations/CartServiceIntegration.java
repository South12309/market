package ru.gb.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.market.api.CartDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceIntegration {

    private final WebClient cartServiceWebClient;

    public CartDto getCurrentCart() {
        return cartServiceWebClient.get()
                .uri("/api/v1/cart")
                // .header("username", username)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clearCart() {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/clear")
                //  .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void addProductToCart(Long id) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/add/" + id)
                //  .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void deleteProductFromCart(Long id) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/delete/" + id)
                //  .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void increaseProductCountInCart(Long id) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/inc/" + id)
                //  .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void decreaseProductCountInCart(Long id) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/dec/" + id)
                //  .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();


    }
}
