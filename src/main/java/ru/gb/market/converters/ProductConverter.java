package ru.gb.market.converters;

import org.springframework.stereotype.Component;
import ru.gb.market.dtos.ProductDto;
import ru.gb.market.entities.Product;


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
