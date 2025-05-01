package com.example.demo.controllers.order;

import com.example.demo.controllers.order.dto.OrderDTO;
import com.example.demo.core.markets.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.orders.order.entity.OrderEntity;
import com.example.demo.core.orders.order.service.OrderService;
import com.example.demo.core.orders.orderItem.entity.OrderItemEntity;
import com.example.demo.core.users.basket.entity.BasketEntity;
import com.example.demo.core.users.basket.service.BasketService;
import com.example.demo.core.users.profile.entity.ProfileEntity;
import com.example.demo.core.users.user.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final BasketService basketService;
    private final UserService userService;

    public OrderController(OrderService orderService, BasketService basketService, UserService userService) {
        this.orderService = orderService;
        this.basketService = basketService;
        this.userService = userService;
    }

    @Transactional
    @RequestMapping("/order/create")
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        logger.info("create Order");

        // валидация DTO
        if (!orderDTO.Validate()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        // подтягиваем профиль
        Optional<ProfileEntity> profile = this.userService.findById(orderDTO.getUserId());
        if (profile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // подтягиваем корзину
        BasketEntity basket = this.basketService.findEntityByProfileId(orderDTO.getUserId());
        if (basket == null || basket.getProducts_in_market().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // создаем заказ
        OrderEntity order = new OrderEntity();

        List<OrderItemEntity> orderItems = new ArrayList<>();
        int totalCount = 0;
        double totalPrice = 0;


        for (ProductsInMarketEntity product : basket.getProducts_in_market()) {

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setProductInMarket(product);
            orderItem.setCountInOrder(product.getCount());
            orderItem.setProductPrice(product.getCost());

            totalCount += product.getCount();
            totalPrice += product.getCost();

            orderItems.add(orderItem);

            // проверочка
            System.out.println("Цена: " + orderItem.getCountInOrder() + "\nКол-во: " + orderItem.getProductPrice()); // не работает
            System.out.println("Цена: " + product.getCost() + "\nКол-во: " + product.getCount());
        }

        order.setProfile(profile.get());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setProducts(orderItems);
        order.setTotalCount(totalCount);
        order.setTotalCost(totalPrice);

        this.orderService.createOrder(order);

        // удаляем товары из корзины - зануляем у магазина
//        this.BasketService.removeFrombacket(orderDTO.getUserId());
//        System.out.println("orderEntity: removeProductInOrder");

        return ResponseEntity.ok().body(null);
    }
//
//    @GetMapping("/order/get")
//    public void getOrder(@RequestParam UUID orderId) {
//    }
//
//    public void getUserOrder(UUID userId) {
//    }
//
//    public void changeStatus(UUID id) {
//    }
}
