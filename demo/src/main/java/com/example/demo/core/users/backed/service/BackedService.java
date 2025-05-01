package com.example.demo.core.users.backet.service;

import com.example.demo.controllers.markets.product.dto.ProductList;
import com.example.demo.controllers.users.backet.dto.backetDTO;
import com.example.demo.core.users.backet.entity.backetEntity;
import com.example.demo.core.users.backet.repository.backetRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class backetService {
    private final backetRepository backetRepository;

    public backetService(backetRepository backetRepository) {
        this.backetRepository = backetRepository;
    }

    // найти
    public List<ProductList> findByProfileId(UUID id) {
        return backetRepository.findByProfileId(id);
    }
    public backetEntity findEntityByProfileId(UUID id) {
        return backetRepository.findEntityByProfileId(id);
    }
    public Optional<backetEntity> findById(UUID id) {
        return backetRepository.findById(id);
    }

    public List<ProductList> findAllProducts() {
        return backetRepository.findAllProducts();
    }

    // добавить
    @Transactional
    public void addTobacket(backetEntity backetEntity) {
        backetRepository.save(backetEntity);
    }
    // удаление
    @Transactional
    public void removeFrombacket(UUID id) {
        backetRepository.deleteByProfileId(id);
    }
}
