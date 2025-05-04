package com.example.demo.core.order.repository;

import com.example.demo.core.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {




//
//    @Query(value = "select new com.example.demo.controllers.order.dto.ReturnValueOrderDTO(" +
//            "oe.profile, " +
//            "(" +
//                "SELECT new com.example.demo.controllers.dto.product.ProductList(" +
//                "pim.id, m.name, m.address, p.name, p.category, p.articleInRegistry, pim.cost, pim.count) " +
//                "FROM OrderItemEntity oep " +
//                "join oep.product pim " +
//                "join pim.product p " +
//                "join pim.market m " +
//                "where oep.order = oe" +
//            "), " +
//            "oe.paymentMethod, oe.status, oe.cost, oe.count) " +
//            "from OrderEntity oe " +
//            "join oe.products " +
//            "where oe.id = ?1")
//    public ReturnValueOrderDTO findByOrderId(UUID orderId);
}
