package com.example.inventoryservice.dtos;

public class InventoryResponseDTO {
    private Long id_inventory;
    private String sku;
    private Integer quantity;
    private boolean inStock;

    public InventoryResponseDTO() {
    }

    public InventoryResponseDTO(String sku, Integer quantity, boolean inStock) {
        this.sku = sku;
        this.quantity = quantity;
        this.inStock = inStock;
    }

    public InventoryResponseDTO(Long id_inventory, String sku, Integer quantity, boolean inStock) {
        this.id_inventory = id_inventory;
        this.sku = sku;
        this.quantity = quantity;
        this.inStock = inStock;
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

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
