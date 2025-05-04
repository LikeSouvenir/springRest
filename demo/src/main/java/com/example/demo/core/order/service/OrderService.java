package com.example.demo.core.order.service;

import com.example.demo.core.order.entity.OrderEntity;
import com.example.demo.core.order.repository.OrderRepository;
import com.example.demo.core.orderItem.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository _orderRepository;
    private final OrderItemRepository _orderItemEntity;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemEntity) {
        this._orderRepository = orderRepository;
        this._orderItemEntity = orderItemEntity;
    }

    // orderRepository //// orderRepository //// orderRepository //
    public OrderEntity createOrder(OrderEntity orderEntity) {
        return _orderRepository.save(orderEntity);
    }
//    public ReturnValueOrderDTO getOrder(UUID orderId) {
//        return _orderRepository.findByOrderId(orderId);
//    }

    public void getUserOrder(UUID userId) {
    }

    public void changeStatus(UUID id) {
    }
}
