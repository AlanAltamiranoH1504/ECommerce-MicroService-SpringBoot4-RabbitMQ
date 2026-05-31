package com.example.productservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    @Indexed(unique = true)
    private String nameProduct;
    private String description;
    private BigDecimal price;

    public Product() {
    }

    public Product(String nameProduct, String description, BigDecimal price) {
        this.nameProduct = nameProduct;
        this.description = description;
        this.price = price;
    }

    public Product(String id, String nameProduct, String description, BigDecimal price) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", nameProduct='" + nameProduct + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
