package com.example.demo.core.order.service;

import com.example.demo.controllers.order.dto.OrderDTO;
import com.example.demo.controllers.order.dto.OrderItemDTO;
import com.example.demo.controllers.order.dto.ReturnValueOrderDTO;
import com.example.demo.controllers.product.dto.ProductDTO;
import com.example.demo.core.cart.entity.CartEntity;
import com.example.demo.core.cart.repository.CartRepository;
import com.example.demo.core.contractor.entity.ContractorEntity;
import com.example.demo.core.order.entity.OrderEntity;
import com.example.demo.core.order.repository.OrderRepository;
import com.example.demo.core.orderItem.entity.OrderItemEntity;
import com.example.demo.core.orderItem.repository.OrderItemRepository;
import com.example.demo.core.productInCart.entity.ProductInCartEntity;
import com.example.demo.core.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.productsInMarket.repository.ProductsInMarketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository _orderRepository;
    private final CartRepository _cartRepository;
    private final ProductsInMarketRepository _productsInMarketRepository;
    private final OrderItemRepository _orderItemRepository;


    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository,
                        ProductsInMarketRepository productsInMarketRepository,
                        OrderItemRepository orderItemRepository) {
        this._orderRepository = orderRepository;
        this._cartRepository = cartRepository;
        this._productsInMarketRepository = productsInMarketRepository;
        this._orderItemRepository = orderItemRepository;
    }

    // OrderRepository //// OrderRepository //// OrderRepository //
//    @Modifying
    public OrderEntity createOrder(OrderDTO newOrder) {
        // подтягиваем корзину
        Optional<CartEntity> cart = this._cartRepository.findByUserId(newOrder.getUserId());
        if (cart.isEmpty()) {
            return null;
        }
        // выбраные товары
        List<ProductInCartEntity> productInCart = cart.get().getProductsInCart();

        // создаем заказ
        OrderEntity order = new OrderEntity();

        List<OrderItemEntity> orderItems = new ArrayList<>();
        double totalPrice = 0;

        // перебираем заказываемые товары
        for (ProductInCartEntity cartProduct : productInCart) {

            OrderItemEntity orderItem = new OrderItemEntity();

            // проходимся по поставщикам и забираем товары
            double costProduct = 0;
            int requireCount = cartProduct.getRequestCount();

            // поставщики
            List<ProductsInMarketEntity> markets = this._productsInMarketRepository.findByProductId(cartProduct.getProduct().getId());

            for (ProductsInMarketEntity market : markets) {
                ContractorEntity newContractor = new ContractorEntity();

                // переносим в заказ необходимо кол-во товара у поставщика
                if (requireCount == 0)
                    break;
                else if (market.getCurrentCount() - requireCount < 0) {
                    newContractor.setAmountCount(market.getCurrentCount());
                    requireCount -= market.getCurrentCount();

                    // обновляем данные
                    this._productsInMarketRepository.updateCount(market.getId(), 0);

                } else {
                    newContractor.setAmountCount(market.getCurrentCount() - requireCount);

                    this._productsInMarketRepository.updateCount(market.getId(), market.getCurrentCount() - requireCount);
                    requireCount = 0;
                }

                costProduct += market.getCurrentCost();

                // устанавливаем мета инфу о поставщиках товара
                newContractor.setOrderItem(orderItem);
                newContractor.setMarket(market.getMarket());
                newContractor.setAmountPrice(market.getCurrentCost());

                // Записываем в contractorsItemEntity
                orderItem.getContractor().add(newContractor);
            }
            // устанавливаем мета инфу о позиции в заказе
            orderItem.setOrder(order);
            orderItem.setProduct(cartProduct.getProduct());
            orderItem.setRequestCount(cartProduct.getRequestCount());
            orderItem.setProductPrice(costProduct);

            totalPrice += costProduct;
            orderItems.add(orderItem);

            System.out.println("item добавлен");
        }

        order.setUserId(newOrder.getUserId());
        order.setUser(cart.get().getUser());
        order.setPaymentMethod(newOrder.getPaymentMethod());
        order.setProducts(orderItems);
        order.setTotalCount(productInCart.size());
        order.setTotalCost(totalPrice);

        System.out.println("Order создан");
        // удаляем товары из корзины - зануляем у магазина
//        this.CartService.removeFromcart(orderDTO.getUserId());
//        System.out.println("orderEntity: removeProductInOrder");

        return _orderRepository.save(order);
    }

    // вместе с продукцией
    @Transactional
    public ReturnValueOrderDTO getOrderById(UUID orderId) {

        Optional<OrderEntity> order = _orderRepository.findById(orderId);
        if (order.isEmpty()) {
            return null;
        }

        return order.get().toDTO();
    }

    @Transactional
    public int updateOrderStatus(UUID orderId, String status) {
        return _orderRepository.updateOrderStatus(orderId, status);
    }


    public List<UUID> getUserOrdersIDs(UUID userId) {
        List<UUID> orders = _orderRepository.findIdsByUserId(userId);
        if (orders.isEmpty()) {
            return null;
        }

        return orders;
    }

    @Transactional
    public List<ReturnValueOrderDTO> getUserOrders(UUID userId) {
        List<OrderEntity> order = _orderRepository.findAllByUserId(userId);
        if (order.isEmpty()) {
            return null;
        }

        return order.stream().map(OrderEntity::toDTO).collect(Collectors.toList());
    }

}
