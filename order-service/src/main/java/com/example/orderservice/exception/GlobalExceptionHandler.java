package com.example.orderservice.exception;

import com.example.orderservice.services.interfaces.IResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private IResponseService iResponseService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> json = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            json.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
    }

    @ExceptionHandler(ListEmptyException.class)
    public ResponseEntity<?> handleListEmptyException(ListEmptyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                iResponseService.generateReponse(false, ex.getMessage())
        );
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<?> handleNotFoundEntityException(NotFoundEntityException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                iResponseService.generateReponse(false, ex.getMessage())
        );
    }

    @ExceptionHandler(StockException.class)
    public ResponseEntity<?> handleStockException(StockException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                iResponseService.generateReponse(false, ex.getMessage())
        );
    }

    @ExceptionHandler(InvetoryServiceDownException.class)
    public ResponseEntity<?> handleInventoryServiceDownException(InvetoryServiceDownException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                iResponseService.generateReponse(false, ex.getMessage())
        );
    }
}
