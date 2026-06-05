package com.example.inventoryservice.exceptions;

public class StockException extends RuntimeException {
    public StockException(String message) {
        super(message);
    }
}
