package com.example.orderservice.dto;

public class InStockResponse {
    private boolean status;
    private String message;

    public InStockResponse() {
    }

    public InStockResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InStockResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
