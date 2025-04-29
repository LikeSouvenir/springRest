package com.example.demo.core.markets.market.repository;


import com.example.demo.controllers.markets.market.dto.GetMarket;
import com.example.demo.core.markets.market.entity.MarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MarketRepository extends JpaRepository<MarketEntity, UUID> {

    @Query(value = "select new com.example.demo.controllers.markets.market.dto.GetMarket (id, name, owner, address) from MarketEntity")
    List<GetMarket> findAllMarkets();

}
