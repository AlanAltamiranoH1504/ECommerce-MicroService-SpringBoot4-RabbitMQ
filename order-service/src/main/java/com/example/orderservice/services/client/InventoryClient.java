package com.example.orderservice.services.client;

import com.example.orderservice.dto.InStockResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface InventoryClient {
    @GetExchange("/inventory/{sku}/quantity/{q}")
    InStockResponse inStock(@PathVariable String sku, @PathVariable Long q);

    @PutExchange("/inventory/decrement/{sku}/{quantity}")
    void decrementStock(@PathVariable String sku, @PathVariable Long quantity);
}
