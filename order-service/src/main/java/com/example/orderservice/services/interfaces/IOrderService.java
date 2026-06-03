package com.example.orderservice.services.interfaces;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    public abstract OrderResponse createOrder(OrderRequest orderRequest);
    public abstract List<OrderResponse> getAllOrders();
    public abstract OrderResponse getOrderById(Long idOrder);
    public abstract void deleteOrderById(Long idOrder);
}
