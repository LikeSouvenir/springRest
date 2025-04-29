package com.example.demo.core.users.backed.service;

import com.example.demo.controllers.markets.product.dto.ProductList;
import com.example.demo.controllers.users.backed.dto.BackedDTO;
import com.example.demo.core.users.backed.entity.BackedEntity;
import com.example.demo.core.users.backed.repository.BackedRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BackedService {
    private final BackedRepository backedRepository;

    public BackedService(BackedRepository backedRepository) {
        this.backedRepository = backedRepository;
    }

    // найти
    public List<ProductList> findByProfileId(UUID id) {
        return backedRepository.findByProfileId(id);
    }

    public List<ProductList> findAllProducts() {
        return backedRepository.findAllProducts();
    }

    // добавить
    @Transactional
    public void addToBacked(BackedEntity backedEntity) {
        backedRepository.save(backedEntity);
    }
    // удаление
    @Transactional
    public void removeFromBacked(UUID id) {
        backedRepository.deleteByProfileId(id);
    }
}
