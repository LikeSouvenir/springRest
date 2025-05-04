package com.example.demo.core.orderItem.repository;

import com.example.demo.core.orderItem.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {

}
