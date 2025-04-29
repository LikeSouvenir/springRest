package com.example.demo.core.markets.market.service;

import com.example.demo.controllers.markets.market.dto.GetMarket;
import com.example.demo.controllers.markets.market.dto.MarketDTO;
import com.example.demo.core.markets.market.entity.MarketEntity;
import com.example.demo.core.markets.market.repository.MarketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MarketService {
    private final MarketRepository marketRepository;

    MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public MarketEntity save(MarketDTO body) {
        return marketRepository.save(new MarketEntity(body.getName(), body.getOwner(), body.getAddress()));
    }

    public MarketEntity findById(UUID id) {
        return marketRepository.findById(id).orElse(null);
    }

    public List<GetMarket> findAllMarkets() {
        return marketRepository.findAllMarkets();
    }
}
