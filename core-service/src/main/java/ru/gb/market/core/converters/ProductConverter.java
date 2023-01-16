package ru.gb.market.core.converters;

import org.springframework.stereotype.Component;
import ru.gb.market.core.dtos.ProductDto;
import ru.gb.market.core.entities.Product;


@Component
public class ProductConverter {
    public ProductDto entityToDto(Product p) {
        ProductDto productDto = new ProductDto();
        productDto.setId(p.getId());
        productDto.setTitle(p.getTitle());
        productDto.setPrice(p.getPrice());
        productDto.setCategoryTitle(p.getCategory().getTitle());
        return productDto;
    }
}
