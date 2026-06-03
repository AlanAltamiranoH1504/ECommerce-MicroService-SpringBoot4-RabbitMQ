package com.example.productservice.services;

import com.example.productservice.services.interfaces.IResponseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseService implements IResponseService {
    @Override
    public Map<String, Object> generateResponseMessage(boolean status, String message) {
        Map<String, Object> json = new HashMap<>();

        json.put("status", status);
        json.put("message", message);

        return json;
    }
}
