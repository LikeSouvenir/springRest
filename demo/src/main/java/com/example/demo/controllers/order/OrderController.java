package com.example.demo.controllers.order;

import com.example.demo.controllers.order.dto.OrderDTO;
import com.example.demo.controllers.order.dto.ReturnValueOrderDTO;
import com.example.demo.controllers.product.dto.UpdateProductDTO;
import com.example.demo.core.order.service.OrderService;
import com.example.demo.utils.exceptions.ValidationException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Order controller",
        description = "Order manipulation add, update, create and etc..."
)
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order create"),
            @ApiResponse(responseCode = "400", description = "Invalid orderDTO"),
            @ApiResponse(responseCode = "204", description = "User cart not found, or mistake in userId")
    })
    @Transactional
    @RequestMapping("/order/create")
    public ResponseEntity<Boolean> createOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "List<DTO> to create new order",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class)
                    ))
            @RequestBody OrderDTO newOrder
    ) {
        logger.info("create Order");

        // валидация DTO
        if (!newOrder.Validate()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Invalid order");
        }

        Boolean order = this.orderService.createOrder(newOrder);
        return ResponseEntity.ok(order);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Find order on id"),
            @ApiResponse(responseCode = "204", description = "User have not orders")
    })
    @GetMapping("/order/get")
    public ReturnValueOrderDTO getOrder(
            @Parameter(description = "UUID order", example = "a8ca68d2-3b30-4c1e-b74a-a9cc1441ge32")
            @RequestParam UUID orderId
    ) {
        logger.info("get order by id: " + orderId);

        return this.orderService.getOrderById(orderId);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful status update"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PatchMapping("order/update")
    public int updateOrderStatus(
            @Parameter(description = "UUID order", example = "a8ca68d2-3b30-4c1e-b74a-a9cc1441ge32")
            @RequestParam UUID orderId,
            @Parameter(description = "Global order status", example = "Задерживается/Обработан/Закрыт")
            @RequestParam String status
    ) {
        logger.info("update order by id: " + orderId);
        return this.orderService.updateOrderStatus(orderId, status);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User orders ids"),
            @ApiResponse(responseCode = "204", description = "User have not orders")
    })
    @GetMapping("order/getIds")
    public List<UUID> getUserOrdersIDs(
            @Parameter(description = "UUID user", example = "a8ca68d2-3b30-4c1e-b74a-a9cc1441ge32")
            @RequestParam UUID userId
    ) {
        logger.info("get user orders ids by userId: " + userId);
        return this.orderService.getUserOrdersIDs(userId);
    }
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All user orders"),
            @ApiResponse(responseCode = "204", description = "User have not orders")
    })
    @GetMapping("order/getUserOrders")
    public List<ReturnValueOrderDTO> getUserOrders(
            @Parameter(description = "UUID user", example = "a8ca68d2-3b30-4c1e-b74a-a9cc1441ge32")
            @RequestParam UUID userId
    ) {
        logger.info("get user orders by userId: " + userId);
        return this.orderService.getUserOrders(userId);
    }
}
