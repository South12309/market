package ru.gb.market.core.converters;

import org.springframework.stereotype.Component;
import ru.gb.market.core.entities.Product;


@Component
public class ProductConverter {
    public ru.gb.market.api.ProductDto entityToDto(Product p) {
        ru.gb.market.api.ProductDto productDto = new ru.gb.market.api.ProductDto();
        productDto.setId(p.getId());
        productDto.setTitle(p.getTitle());
        productDto.setPrice(p.getPrice());
        productDto.setCategoryTitle(p.getCategory().getTitle());
        return productDto;
    }
}
