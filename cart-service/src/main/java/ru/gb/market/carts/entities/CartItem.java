package ru.gb.market.carts.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_title")
    private String productTitle;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price_per_product")
    private BigDecimal pricePerProduct;
    @Column(name = "price")
    private BigDecimal price;
}