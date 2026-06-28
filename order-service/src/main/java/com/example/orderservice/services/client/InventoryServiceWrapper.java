package com.example.orderservice.services.client;

import com.example.orderservice.dto.InStockResponse;
import com.example.orderservice.exception.InvetoryServiceDownException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InventoryServiceWrapper {
    @Autowired
    InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventory", fallbackMethod = "checkStockFallback")
    @Retry(name = "inventoryRetry")
//    @TimeLimiter(name = "inventoryTimeout")
    public InStockResponse checkStock(String sku, Long quantityRequired) {
        return inventoryClient.inStock(sku, quantityRequired);
    }

    @CircuitBreaker(name = "inventory", fallbackMethod = "decrementStockFallback")
    public void decrementStock(String sku, Long quantityRequired) {
        inventoryClient.decrementStock(sku, quantityRequired);
    }

    // ! FALLBACK METHODS
    public InStockResponse checkStockFallback(String sku, Long quantityRequired, Throwable ex) {
        throw new InvetoryServiceDownException("El servicio de invetario no está disponible. No se puede verificar el stock de " + sku);
    }

    public void decrementStockFallback(String sku, Long quantityRequired, Throwable ex) {
        throw new InvetoryServiceDownException("No se puede actualizar el invetario para el producto " + sku + ". Intentalo más tarde");
    }

}
