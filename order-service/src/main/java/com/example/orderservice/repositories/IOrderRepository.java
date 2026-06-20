package com.example.orderservice.repositories;

import com.example.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user_id = :userId")
    public abstract List<Order> findByUserId(@Param("userId") String userId);
}
