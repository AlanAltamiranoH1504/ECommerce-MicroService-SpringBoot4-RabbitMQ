package com.example.inventoryservice.services;

import com.example.inventoryservice.dtos.InventoryRequestDTO;
import com.example.inventoryservice.dtos.InventoryResponseDTO;
import com.example.inventoryservice.exceptions.CreateEntityException;
import com.example.inventoryservice.exceptions.NotFoundException;
import com.example.inventoryservice.models.Inventory;
import com.example.inventoryservice.repositories.IInventoryRepository;
import com.example.inventoryservice.services.interfaces.IInventoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService implements IInventoryService {
    @Autowired
    private IInventoryRepository iInventoryRepository;

    @Override
    public boolean isInStock(String sku, Integer quantity) {
        return iInventoryRepository.findBySku(sku).map(invet -> {
            return invet.getQuantity() >= quantity;
        }).orElse(false);
    }

    @Override
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO) {
        // ! INVENTORY BY SKU EXISTS
        boolean existeInventory = iInventoryRepository.existsBySku(inventoryRequestDTO.getSku());
        if (existeInventory) {
            throw new CreateEntityException("Ya existe un invetario para el sku " + inventoryRequestDTO.getSku());
        }

        Inventory inventoryToCreate = new Inventory(inventoryRequestDTO.getSku(), inventoryRequestDTO.getQuantity());
        iInventoryRepository.save(inventoryToCreate);

        return new InventoryResponseDTO(inventoryToCreate.getId_inventory(), inventoryToCreate.getSku(), inventoryToCreate.getQuantity(), true);
    }

    @Override
    public List<InventoryResponseDTO> getAllInventories() {
        return iInventoryRepository.findAll()
                .stream()
                .map(inventory -> {
                    return new InventoryResponseDTO(
                            inventory.getId_inventory(),
                            inventory.getSku(),
                            inventory.getQuantity(),
                            this.isInStock(inventory.getSku(), 1)
                    );
                }).toList();
    }

    @Override
    public InventoryResponseDTO updateInventory(Long idInventory, InventoryRequestDTO inventoryRequestDTO) {
        Inventory inventoryToUpdate = iInventoryRepository.findById(idInventory).orElseThrow(() -> new NotFoundException("No se encontro un inventario con id" + idInventory));

        BeanUtils.copyProperties(inventoryRequestDTO, inventoryToUpdate);
        iInventoryRepository.save(inventoryToUpdate);
        return new InventoryResponseDTO(
                inventoryToUpdate.getId_inventory(),
                inventoryToUpdate.getSku(),
                inventoryToUpdate.getQuantity(),
                this.isInStock(inventoryToUpdate.getSku(), 1)
        );
    }

    @Override
    public void deleteInventory(Long idInventory) {
        if (!iInventoryRepository.existsById(idInventory)) {
            throw new NotFoundException("No existe un inventario con el id: " + idInventory);
        }
        iInventoryRepository.deleteById(idInventory);
    }
}
