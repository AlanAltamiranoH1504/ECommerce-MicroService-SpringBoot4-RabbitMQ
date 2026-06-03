package com.example.orderservice.dto;

import java.math.BigDecimal;

public class OrderLineItemResponse {
    private Long id_order_line;
    private String sku;
    private BigDecimal price;
    private Long quantity;

    public OrderLineItemResponse(Long id_order_line, String sku, BigDecimal price, Long quantity) {
        this.id_order_line = id_order_line;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId_order_line() {
        return id_order_line;
    }

    public void setId_order_line(Long id_order_line) {
        this.id_order_line = id_order_line;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
