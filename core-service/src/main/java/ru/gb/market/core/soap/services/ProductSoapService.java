package ru.gb.market.core.soap.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.market.core.entities.Product;
import ru.gb.market.api.ResourceNotFoundException;
import ru.gb.market.core.repositories.ProductRepository;
import ru.gb.market.core.soap.dto.ProductSoap;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSoapService {
    private final ProductRepository productRepository;

    public static final Function<Product, ProductSoap> functionEntityToSoap = se -> {
        ProductSoap s = new ProductSoap();
        s.setId(se.getId());
        s.setTitle(se.getTitle());
        s.setPrice(se.getPrice().intValue());
        return s;
    };

    public List<ProductSoap> getAllProducts() {
        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public ProductSoap getByName(String name) {
        return productRepository.findByTitle(name).map(functionEntityToSoap).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден"));
    }
}
