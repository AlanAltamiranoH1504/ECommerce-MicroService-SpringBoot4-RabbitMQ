package com.example.orderservice.services;

import com.example.orderservice.services.interfaces.IResponseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseService implements IResponseService {
    @Override
    public Map<String, Object> generateReponse(boolean status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        return response;
    }
}
