package com.example.productservice.services.interfaces;

import java.util.Map;

public interface IResponseService {
    public abstract Map<String, Object> generateResponseMessage(boolean status, String message);
}
