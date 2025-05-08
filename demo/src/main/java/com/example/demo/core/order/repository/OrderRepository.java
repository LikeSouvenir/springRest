package com.example.demo.core.order.repository;

import com.example.demo.core.order.entity.OrderEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    @Modifying
    @Query(value = "update OrderEntity oe set oe.status = ?2 where oe.id = ?1")
    int updateOrderStatus(UUID orderId, String status);

    List<OrderEntity> findAllByUserId(UUID userId);

    @Query(value = "select oe.id from OrderEntity oe where oe.user.id = ?1")
    List<UUID> findIdsByUserId(UUID userId);
}
