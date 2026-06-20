package com.example.orderservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "tbl_orders")
@JsonPropertyOrder({"id_order", "order_number", "created_at", "updated_at"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_order;
    private String order_number;
    private String user_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    // ! Una orden puede tener muchas lienas de orden
    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    public Order() {
    }

    public Order(String order_number) {
        this.order_number = order_number;
    }

    public Order(String order_number, LocalDateTime created_at, LocalDateTime updated_at) {
        this.order_number = order_number;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Order(Long id_order, String order_number, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id_order = id_order;
        this.order_number = order_number;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @PrePersist
    public void prePersist() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    public Long getId_order() {
        return id_order;
    }

    public void setId_order(Long id_order) {
        this.id_order = id_order;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
