package com.example.productservice.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(String id, String nameProduct, String description, BigDecimal price) {
}
