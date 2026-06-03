package com.example.inventoryservice.controllers;

import com.example.inventoryservice.dtos.InventoryRequestDTO;
import com.example.inventoryservice.services.interfaces.IInventoryService;
import com.example.inventoryservice.services.interfaces.IResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private IInventoryService iInventoryService;
    @Autowired
    private IResponseService iResponseService;

    @GetMapping("")
    public ResponseEntity<?> getAllInventories() {
        return ResponseEntity.status(HttpStatus.OK).body(iInventoryService.getAllInventories());
    }

    @PostMapping("")
    public ResponseEntity<?> createInventory(@Valid @RequestBody InventoryRequestDTO inventoryRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iInventoryService.createInventory(inventoryRequestDTO));
    }

    @DeleteMapping("{idInventory}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long idInventory) {
        iInventoryService.deleteInventory(idInventory);
        return ResponseEntity.status(HttpStatus.OK).body(
                iResponseService.generateResponse(true, "Inventario eliminado")
        );
    }

    @PutMapping("{idInventory}")
    public ResponseEntity<?> updateInventory(@Valid @RequestBody InventoryRequestDTO inventoryRequestDTO, @PathVariable Long idInventory) {
        return ResponseEntity.status(HttpStatus.OK).body(
                iInventoryService.updateInventory(idInventory, inventoryRequestDTO)
        );
    }
}
