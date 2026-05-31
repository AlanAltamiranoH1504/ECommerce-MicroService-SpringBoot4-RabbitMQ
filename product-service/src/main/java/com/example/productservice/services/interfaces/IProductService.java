package com.example.productservice.services.interfaces;

import com.example.productservice.dto.ProductRequestDTO;
import com.example.productservice.dto.ProductResponseDTO;
import com.example.productservice.models.Product;

import java.util.List;

public interface IProductService {
    public abstract List<ProductResponseDTO> getProducts();
    public abstract ProductResponseDTO getProduct(String id);
    public abstract ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    public abstract ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO);
    public abstract boolean deleteProduct(String id);
}
