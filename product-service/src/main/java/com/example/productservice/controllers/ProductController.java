package com.example.productservice.controllers;

import com.example.productservice.dto.ProductRequestDTO;
import com.example.productservice.dto.ProductResponseDTO;
import com.example.productservice.services.interfaces.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {
    @Autowired
    private IProductService iProductService;

    @GetMapping("")
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.getProducts());
    }

    @PostMapping("")
    public ResponseEntity<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iProductService.createProduct(productRequestDTO));
    }

    @GetMapping("{idProduct}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable String idProduct) {
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.getProduct(idProduct));
    }

    @PutMapping("{idProduct}")
    public ResponseEntity<?> update(@Valid @RequestBody ProductRequestDTO productRequestDTO, @PathVariable String idProduct) {
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.updateProduct(idProduct, productRequestDTO));
    }

    @DeleteMapping("{idProduct}")
    public ResponseEntity<?> deleteById(@PathVariable String idProduct) {
        return ResponseEntity.status(HttpStatus.OK).body(iProductService.deleteProduct(idProduct));
    }
}
