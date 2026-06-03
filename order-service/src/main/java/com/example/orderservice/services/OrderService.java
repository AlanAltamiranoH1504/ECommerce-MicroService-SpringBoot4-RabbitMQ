package com.example.orderservice.services;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.exception.ListEmptyException;
import com.example.orderservice.exception.NotFoundEntityException;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderLineItem;
import com.example.orderservice.repositories.IOrderRepository;
import com.example.orderservice.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository iOrderRepository;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        String orderNumber = UUID.randomUUID().toString();
        Order orderToCreate = new Order(orderNumber);

        for (var item: orderRequest.getOrderLineItemsRequest()) {
            OrderLineItem orderLineItem = new OrderLineItem(item.getSku(), item.getPrice(), item.getQuantity(),orderToCreate, LocalDateTime.now(), LocalDateTime.now());
            orderToCreate.getOrderLineItems().add(orderLineItem);
        }
        iOrderRepository.save(orderToCreate);
        return new OrderResponse(
                orderToCreate.getId_order(),
                orderToCreate.getOrder_number(),
                orderToCreate.getOrderLineItems()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponse> getAllOrders() {
        List<OrderResponse> orderResponseList = iOrderRepository.findAll()
                .stream()
                .map(order -> {
                    return new OrderResponse(order.getId_order(), order.getOrder_number(), order.getOrderLineItems());
                }).toList();
        if (orderResponseList.isEmpty()) {
            throw new ListEmptyException("La lista de ordenes se encuentra vacia");
        }
        return orderResponseList;
    }

    @Override
    public OrderResponse getOrderById(Long idOrder) {
        Order orderToFound = iOrderRepository.findById(idOrder).orElseThrow(() -> new NotFoundEntityException("No se encontro una orden con el id: " + idOrder));

        return new OrderResponse(
                orderToFound.getId_order(),
                orderToFound.getOrder_number(),
                orderToFound.getOrderLineItems()
        );
    }

    @Override
    public void deleteOrderById(Long idOrder) {
        Order orderToDelete = iOrderRepository.findById(idOrder).orElseThrow(() -> new NotFoundEntityException("No se encontro una orden con el id: " + idOrder));
        iOrderRepository.delete(orderToDelete);
    }
}
