package com.example.inventoryservice.services.interfaces;

import java.util.Map;

public interface IResponseService {
    public abstract Map<String, Object> generateResponse(boolean status, String message);
}
