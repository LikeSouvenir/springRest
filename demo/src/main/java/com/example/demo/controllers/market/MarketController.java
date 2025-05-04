package com.example.demo.controllers.market;

import com.example.demo.controllers.market.dto.GetMarket;
import com.example.demo.controllers.market.dto.MarketDTO;
import com.example.demo.core.market.entity.MarketEntity;
import com.example.demo.core.market.service.MarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MarketController {
    private final Logger logger = LoggerFactory.getLogger(MarketController.class);

    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping("/markets")
    public ResponseEntity<List<GetMarket>> getMarkets() {
        this.logger.info("Getting markets");

        return ResponseEntity.ok(this.marketService.findAllMarkets());
    }

    @PostMapping("/market/create")
    public ResponseEntity<MarketEntity> addMarket(@RequestBody MarketDTO body) {
        this.logger.info("Adding market: {}", body);

        if (!body.Validate()) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(this.marketService.save(body));
    }
}