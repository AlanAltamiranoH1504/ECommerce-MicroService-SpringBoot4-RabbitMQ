package com.example.orderservice.services.client;

import com.example.orderservice.dto.InStockResponse;
import com.example.orderservice.exception.InvetoryServiceDownException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class InventoryServiceWrapper {
    @Autowired
    InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventory", fallbackMethod = "checkStockFallback")
    @Retry(name = "inventoryRetry")
    @TimeLimiter(name = "inventoryTimeout")
    public CompletableFuture<InStockResponse> checkStock(String sku, Long quantityRequired) {
        return CompletableFuture.supplyAsync(() -> inventoryClient.inStock(sku, quantityRequired));
    }

    @CircuitBreaker(name = "inventory", fallbackMethod = "decrementStockFallback")
    public void decrementStock(String sku, Long quantityRequired) {
        inventoryClient.decrementStock(sku, quantityRequired);
    }

    // ! FALLBACK METHODS
    public CompletableFuture<InStockResponse> checkStockFallback(String sku, Long quantityRequired, Throwable ex) {
        if (ex instanceof java.util.concurrent.TimeoutException) {
            System.out.println("¡La llamada a inventario superó el tiempo límite!");
        }
        throw new InvetoryServiceDownException("El servicio de invetario no está disponible. No se puede verificar el stock de " + sku);
    }

    public void decrementStockFallback(String sku, Long quantityRequired, Throwable ex) {
        throw new InvetoryServiceDownException("No se puede actualizar el invetario para el producto " + sku + ". Intentalo más tarde");
    }

}
