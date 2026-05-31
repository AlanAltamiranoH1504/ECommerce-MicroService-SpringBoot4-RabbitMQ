package com.example.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "El nombre del producto es requerido")
        @Length(max = 150, message = "El nombre del producto no puede ser mayor a 150 caracteres")
        String nameProduct,

        @NotBlank(message = "La descripcion del producto es requerida")
        @Length(max = 2000, message = "La descripción es demasiado larga")
        String description,

        @NotNull(message = "El precio es requerido")
        @Positive(message = "El precio debe ser un valor positivo")
        BigDecimal price) {
}
