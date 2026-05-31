package com.example.productservice.dataLoader;

import com.example.productservice.models.Product;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TestDataLoader implements CommandLineRunner {
    private ProductRepository productRepository;

    public TestDataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Product productTest = new Product("ProductoPrueba", "Description", BigDecimal.valueOf(154.25));
//        productRepository.save(productTest);
        System.out.println("El producto se ha guardado: " + productTest);
    }
}
