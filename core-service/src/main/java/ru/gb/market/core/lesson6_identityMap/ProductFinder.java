package ru.gb.market.core.lesson6_identityMap;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.gb.market.core.entities.Product;

@Component
@Getter
public class ProductFinder {
    private final IdentityMap identityMap = new IdentityMap();


    public boolean isProductPresentInMapById(Long id) {
        return identityMap.isPresent(id);
    }

    public void putNewTmpProductInMap(Product product) {
        identityMap.addProduct(product);
    }

    public Product findProductInMapById(Long id) {
        return identityMap.getProduct(id);

    }
}
