package ru.gb.market.core.lesson6_identityMap;

import lombok.extern.slf4j.Slf4j;
import ru.gb.market.core.entities.Product;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class IdentityMap {
    private Map<Long, Product> productMap = new HashMap<>();

    public void addProduct(Product product) {
        if (!productMap.containsKey(product.getId())) {
            productMap.put(product.getId(), product);
        } else {
            log.info("Продукт уже в мап");
        }
    }

    public Product getProduct(Long id) {
        Product product = productMap.get(id);
        if (product == null) {
            log.info("id не найден в мап");
        }
        return product;
    }

    public boolean isPresent(Long id) {
        return productMap.get(id) != null;
    }
}
