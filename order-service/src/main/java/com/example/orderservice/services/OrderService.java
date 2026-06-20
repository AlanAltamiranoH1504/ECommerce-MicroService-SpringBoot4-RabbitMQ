package com.example.orderservice.services;

import com.example.orderservice.config.WebClientConfig;
import com.example.orderservice.dto.InStockResponse;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.exception.ListEmptyException;
import com.example.orderservice.exception.NotFoundEntityException;
import com.example.orderservice.exception.StockException;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderLineItem;
import com.example.orderservice.repositories.IOrderRepository;
import com.example.orderservice.services.client.InventoryClient;
import com.example.orderservice.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository iOrderRepository;
    //    @Autowired
//    private WebClient webClient;
    @Autowired
    private InventoryClient inventoryClient;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest, String userId) {
        String orderNumber = UUID.randomUUID().toString();
        Order orderToCreate = new Order(orderNumber);
        orderToCreate.setUser_id(userId);

        for (var item : orderRequest.getOrderLineItemsRequest()) {
            // * VALIDACION DE STOCK CON MS DE INVETARIO
            String sku = item.getSku();
            Long quantityRequired = item.getQuantity();
            InStockResponse inStock = inventoryClient.inStock(sku, quantityRequired);
//            InStockResponse inStock = webClient // * PETICION BLOQUEANTE
//                    .get()
//                    .uri("http://localhost:8081/inventory/{sku}/quantity/{q}", sku, quantityRequired)
//                    .retrieve()
//                    .bodyToMono(InStockResponse.class)
//                    .block();

            if (!inStock.isStatus()) {
                throw new StockException("No existe stock suficiente para el producto con sku " + sku);
            }

            // * STOCK DECREMENT
            inventoryClient.decrementStock(sku, quantityRequired);
//            webClient
//                    .put()
//                    .uri("http://localhost:8081/inventory/decrement/{sku}/{quantity}", sku, quantityRequired)
//                    .retrieve()
//                    .bodyToMono(Void.class)
//                    .block();

            OrderLineItem orderLineItem = new OrderLineItem(item.getSku(), item.getPrice(), item.getQuantity(), orderToCreate, LocalDateTime.now(), LocalDateTime.now());
            orderToCreate.getOrderLineItems().add(orderLineItem);
        }
        iOrderRepository.save(orderToCreate);
        return new OrderResponse(
                orderToCreate.getId_order(),
                orderToCreate.getOrder_number(),
                orderToCreate.getOrderLineItems()
        );
    }

//    @Transactional(readOnly = true)
//    @Override
//    public List<OrderResponse> getAllOrders() {
//        List<OrderResponse> orderResponseList = iOrderRepository.findAll()
//                .stream()
//                .map(order -> {
//                    return new OrderResponse(order.getId_order(), order.getOrder_number(), order.getOrderLineItems());
//                }).toList();
//        if (orderResponseList.isEmpty()) {
//            throw new ListEmptyException("La lista de ordenes se encuentra vacia");
//        }
//        return orderResponseList;
//    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponse> getOrders(String userId, boolean isAdmin) {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        if (isAdmin) {
            orderResponseList = iOrderRepository.findAll()
                    .stream()
                    .map(order -> {
                        return new OrderResponse(order.getId_order(), order.getOrder_number(), order.getOrderLineItems());
                    }).toList();
        } else {
            orderResponseList = iOrderRepository.findByUserId(userId)
                    .stream()
                    .map(order -> {
                        return new OrderResponse(order.getId_order(), order.getOrder_number(), order.getOrderLineItems());
                    }).toList();
        }
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
