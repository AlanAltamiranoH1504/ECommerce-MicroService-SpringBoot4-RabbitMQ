package com.example.inventoryservice.exceptions;

public class CreateEntityException extends RuntimeException {
    public CreateEntityException(String message) {
        super(message);
    }
}
