package com.example.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    @NotEmpty(message = "Los productos de la orden son requeridos")
    @Valid
    private List<OrderLineItemRequest> orderLineItemsRequest = new ArrayList<>();

    public OrderRequest() {
    }

    public OrderRequest(List<OrderLineItemRequest> orderLineItemsRequest) {
        this.orderLineItemsRequest = orderLineItemsRequest;
    }

    public List<OrderLineItemRequest> getOrderLineItemsRequest() {
        return orderLineItemsRequest;
    }

    public void setOrderLineItemsRequest(List<OrderLineItemRequest> orderLineItemsRequest) {
        this.orderLineItemsRequest = orderLineItemsRequest;
    }
}
