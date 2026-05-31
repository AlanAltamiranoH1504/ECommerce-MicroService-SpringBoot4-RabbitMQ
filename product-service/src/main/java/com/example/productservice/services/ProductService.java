package com.example.productservice.services;

import com.example.productservice.dto.ProductRequestDTO;
import com.example.productservice.dto.ProductResponseDTO;
import com.example.productservice.exceptions.NotFoundException;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.ProductRepository;
import com.example.productservice.services.interfaces.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository iProductRepository;

    @Override
    public List<ProductResponseDTO> getProducts() {
        return iProductRepository.findAll()
                .stream()
                .map(product -> {
                    return new ProductResponseDTO(product.getId(), product.getNameProduct(), product.getDescription(), product.getPrice());
                }).toList();
    }

    @Override
    public ProductResponseDTO getProduct(String id) {
        Product productToFind = iProductRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado en la base de datos"));
        return new ProductResponseDTO(
                productToFind.getId(),
                productToFind.getNameProduct(),
                productToFind.getDescription(),
                productToFind.getPrice()
        );
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product(productRequestDTO.nameProduct(), productRequestDTO.description(), productRequestDTO.price());
        iProductRepository.save(product);

        return new ProductResponseDTO(product.getId(), product.getNameProduct(), product.getDescription(), product.getPrice());
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productFounded = this.getProduct(id);

        Product productToUpdate = new Product(id, productFounded.nameProduct(), productFounded.description(), productFounded.price());
        BeanUtils.copyProperties(productRequestDTO, productToUpdate);
        iProductRepository.save(productToUpdate);
        return new ProductResponseDTO(productToUpdate.getId(), productToUpdate.getNameProduct(), productToUpdate.getDescription(), productToUpdate.getPrice());
    }

    @Override
    public boolean deleteProduct(String id) {
        ProductResponseDTO productFounded = this.getProduct(id);
        iProductRepository.deleteById(id);
        return true;
    }
}
