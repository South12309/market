package ru.gb.market.api;

import java.math.BigDecimal;
import java.util.List;


public class OrderDto {
    private Long id;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    private String address;
    private String phone;

    public static class OrderDtoBuilder {
        private Long id;
        private List<OrderItemDto> items;
        private BigDecimal totalPrice;
        private String address;
        private String phone;

        public OrderDto build() {
            return new OrderDto(this);
        }

        public OrderDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderDtoBuilder items(List<OrderItemDto> items) {
            this.items = items;
            return this;
        }

        public OrderDtoBuilder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }


        public OrderDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public OrderDtoBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Long getId() {
            return id;
        }

        public List<OrderItemDto> getItems() {
            return items;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }
    }

    public static OrderDtoBuilder builder() { return new OrderDtoBuilder();}

    public OrderDto(Long id, List<OrderItemDto> items, BigDecimal totalPrice, String address, String phone) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
        this.address = address;
        this.phone = phone;
    }

    public OrderDto(OrderDtoBuilder orderDtoBuilder) {
        this.id = orderDtoBuilder.getId();
        this.items = orderDtoBuilder.getItems();
        this.totalPrice = orderDtoBuilder.getTotalPrice();
        this.address = orderDtoBuilder.getAddress();
        this.phone = orderDtoBuilder.getPhone();
    }

    public OrderDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
