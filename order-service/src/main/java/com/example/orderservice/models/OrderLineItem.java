package com.example.orderservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity()
@Table(name = "tbl_order_line_items")
@JsonPropertyOrder({"id_order_line", "sku", "price", "quantity"})
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_order_line;
    private String sku;
    private BigDecimal price;
    private Long quantity;
    // ! Una linea de order pertenece a una orden
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private Order order;
    private LocalDateTime created_at;
    private LocalDateTime updatet_at;

    @PrePersist
    public void prePersist() {
        this.created_at = LocalDateTime.now();
        this.updatet_at = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatet_at = LocalDateTime.now();
    }

    public OrderLineItem() {
    }

    public OrderLineItem(String sku, BigDecimal price, Long quantity, Order order) {
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }

    public OrderLineItem(String sku, BigDecimal price, Long quantity, Order order, LocalDateTime updatet_at) {
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.updatet_at = updatet_at;
    }

    public OrderLineItem(String sku, BigDecimal price, Long quantity, Order order, LocalDateTime created_at, LocalDateTime updatet_at) {
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.created_at = created_at;
        this.updatet_at = updatet_at;
    }

    public OrderLineItem(Long id_order_line, String sku, BigDecimal price, Long quantity, Order order, LocalDateTime created_at, LocalDateTime updatet_at) {
        this.id_order_line = id_order_line;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.created_at = created_at;
        this.updatet_at = updatet_at;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdatet_at() {
        return updatet_at;
    }

    public void setUpdatet_at(LocalDateTime updatet_at) {
        this.updatet_at = updatet_at;
    }
}
