package com.example.orderservice.controllers;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.services.interfaces.IOrderService;
import com.example.orderservice.services.interfaces.IResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IResponseService iResponseService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iOrderService.createOrder(orderRequest));
    }

    @GetMapping("")
    public ResponseEntity<?> findAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(iOrderService.getAllOrders());
    }

    @GetMapping("{idOrder}")
    public ResponseEntity<?> findOrderById(@PathVariable Long idOrder) {
        return ResponseEntity.status(HttpStatus.OK).body(iOrderService.getOrderById(idOrder));
    }

    @DeleteMapping("{idOrder}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long idOrder) {
        iOrderService.deleteOrderById(idOrder);
        return ResponseEntity.status(HttpStatus.OK).body(iResponseService.generateReponse(true, "Orden eliminada correctamente"));
    }
}
