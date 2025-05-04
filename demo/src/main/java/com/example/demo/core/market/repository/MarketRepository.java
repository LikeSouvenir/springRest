package com.example.demo.core.market.repository;


import com.example.demo.controllers.market.dto.GetMarket;
import com.example.demo.core.market.entity.MarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MarketRepository extends JpaRepository<MarketEntity, UUID> {

    @Query(value = "select new com.example.demo.controllers.market.dto.GetMarket (id, name, owner, address) from MarketEntity")
    List<GetMarket> findAllMarkets();

}
