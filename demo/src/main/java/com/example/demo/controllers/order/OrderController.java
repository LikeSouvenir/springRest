package com.example.demo.controllers.order;

import com.example.demo.controllers.order.dto.OrderDTO;
import com.example.demo.controllers.order.dto.ReturnValueOrderDTO;
import com.example.demo.core.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.order.entity.OrderEntity;
import com.example.demo.core.order.service.OrderService;
import com.example.demo.core.orderItem.entity.OrderItemEntity;
import com.example.demo.core.cart.entity.CartEntity;
import com.example.demo.core.cart.service.CartService;
import com.example.demo.core.profile.entity.ProfileEntity;
import com.example.demo.core.user.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    @RequestMapping("/order/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO newOrder) {
        logger.info("create Order");

        // валидация DTO
        if (!newOrder.Validate()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        OrderEntity order = this.orderService.createOrder(newOrder);
        return ResponseEntity.ok().body(null);
    }


    @GetMapping("/order/get")
    public ReturnValueOrderDTO getOrder(@RequestParam UUID orderId) {

        return this.orderService.getOrderById(orderId);
    }

    @PatchMapping("order/update")
    public int updateOrderStatus(@RequestParam UUID orderId, @RequestParam String status) {
        return this.orderService.updateOrderStatus(orderId, status);
    }

    @GetMapping("order/getIds")
    public List<UUID> getUserOrdersIDs(@RequestParam UUID userId) {
        return this.orderService.getUserOrdersIDs(userId);
    }

    @GetMapping("order/getUserOrders")
    public List<ReturnValueOrderDTO> getUserOrders(@RequestParam UUID userId) {
        return this.orderService.getUserOrders(userId);
    }
}
