package com.example.inventoryservice.models;

import jakarta.persistence.*;

@Entity()
@Table(name = "tbl_inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_inventory;
    private String sku;
    private Integer quantity;

    public Inventory() {
    }

    public Inventory(String sku, Integer quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }

    public Inventory(Long id_inventory, String sku, Integer quantity) {
        this.id_inventory = id_inventory;
        this.sku = sku;
        this.quantity = quantity;
    }

    public Long getId_inventory() {
        return id_inventory;
    }

    public void setId_inventory(Long id_inventory) {
        this.id_inventory = id_inventory;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
