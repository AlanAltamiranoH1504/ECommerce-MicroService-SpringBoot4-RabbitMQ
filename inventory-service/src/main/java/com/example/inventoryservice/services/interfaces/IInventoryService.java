package com.example.inventoryservice.services.interfaces;

import com.example.inventoryservice.dtos.InventoryRequestDTO;
import com.example.inventoryservice.dtos.InventoryResponseDTO;

import java.util.List;

public interface IInventoryService {
    public abstract boolean isInStock(String sku, Integer quantity);
    public abstract InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO);
    public abstract List<InventoryResponseDTO> getAllInventories();
    public abstract InventoryResponseDTO updateInventory(Long idInventory, InventoryRequestDTO inventoryRequestDTO);
    public abstract void deleteInventory(Long idInventory);
    public abstract void decrementInventory(String sku, Integer quantity);
}
