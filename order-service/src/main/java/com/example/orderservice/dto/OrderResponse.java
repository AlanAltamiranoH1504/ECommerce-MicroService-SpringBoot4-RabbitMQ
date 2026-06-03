package com.example.orderservice.dto;

import com.example.orderservice.models.OrderLineItem;

import java.util.ArrayList;
import java.util.List;

public class OrderResponse {
    private Long id_order;
    private String order_number;
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    public OrderResponse(Long id_order, String order_number, List<OrderLineItem> orderLineItems) {
        this.id_order = id_order;
        this.order_number = order_number;
        this.orderLineItems = orderLineItems;
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

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }
}
