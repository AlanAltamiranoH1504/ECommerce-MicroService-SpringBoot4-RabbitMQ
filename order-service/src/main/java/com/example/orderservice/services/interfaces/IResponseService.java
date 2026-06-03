package com.example.orderservice.services.interfaces;

import java.util.Map;

public interface IResponseService {
    public abstract Map<String, Object> generateReponse(boolean status, String message);
}
