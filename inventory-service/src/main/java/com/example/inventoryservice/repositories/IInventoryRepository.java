package com.example.inventoryservice.repositories;

import com.example.inventoryservice.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT i FROM Inventory i WHERE i.sku = :sku")
    Optional<Inventory> findBySku(@Param("sku") String sku);

    @Query("SELECT COUNT(i) > 0 FROM Inventory i WHERE i.sku = :sku")
    boolean existsBySku(@Param("sku") String sku);
}
