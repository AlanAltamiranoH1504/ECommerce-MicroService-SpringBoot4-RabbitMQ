package com.example.orderservice.controllers;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.services.interfaces.IOrderService;
import com.example.orderservice.services.interfaces.IResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IResponseService iResponseService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iOrderService.createOrder(orderRequest, jwt.getSubject()));
    }

    @GetMapping("")
    public ResponseEntity<?> findAllOrders(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        boolean isAdmin = false;

        Map<String, Object> realmAcess = jwt.getClaim("realm_access");
        if (realmAcess != null && realmAcess.containsKey("roles")) {
            List<String> roles = (List<String>) realmAcess.get("roles");
            if (roles.contains("ADMIN")) {
                isAdmin = true;
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(iOrderService.getOrders(userId, isAdmin));
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
