package com.example.orderservice.exception;

public class ListEmptyException extends RuntimeException {
    public ListEmptyException(String message) {
        super(message);
    }
}
