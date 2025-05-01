package com.example.demo.core.users.basket.service;

import com.example.demo.controllers.markets.product.dto.ProductList;
import com.example.demo.controllers.users.basket.dto.BasketDTO;
import com.example.demo.core.users.basket.entity.BasketEntity;
import com.example.demo.core.users.basket.repository.BasketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasketService {
    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    // найти
    public List<ProductList> findByProfileId(UUID id) {
        return basketRepository.findByProfileId(id);
    }
    public BasketEntity findEntityByProfileId(UUID id) {
        return basketRepository.findEntityByProfileId(id);
    }
    public Optional<BasketEntity> findById(UUID id) {
        return basketRepository.findById(id);
    }

    public List<ProductList> findAllProducts() {
        return basketRepository.findAllProducts();
    }

    // добавить
    @Transactional
    public void addTobacket(BasketEntity backetEntity) {
        basketRepository.save(backetEntity);
    }
    // удаление
    @Transactional
    public void removeFrombacket(UUID id) {
        basketRepository.deleteByProfileId(id);
    }
}
