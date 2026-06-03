package com.example.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public class OrderLineItemRequest {
    @NotNull(message = "El sku es requerido")
    @Length(max = 255, message = "La longitud no puede ser mayor a 255 caracteres")
    private String sku;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser positivo")
    private BigDecimal price;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor o igual a 1")
    private Long quantity;

    public OrderLineItemRequest() {
    }

    public OrderLineItemRequest(String sku, BigDecimal price, Long quantity) {
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
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
