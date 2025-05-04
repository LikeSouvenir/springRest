package com.example.demo.core.market.service;

import com.example.demo.controllers.market.dto.GetMarket;
import com.example.demo.controllers.market.dto.MarketDTO;
import com.example.demo.core.market.entity.MarketEntity;
import com.example.demo.core.market.repository.MarketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MarketService {
    private final MarketRepository marketRepository;

    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public MarketEntity save(MarketDTO body) {
        return marketRepository.save(new MarketEntity(body.getName(), body.getOwner(), body.getAddress()));
    }

    public Optional<MarketEntity> findById(UUID id) {
        return marketRepository.findById(id);
    }

    public List<GetMarket> findAllMarkets() {
        return marketRepository.findAllMarkets();
    }
}
