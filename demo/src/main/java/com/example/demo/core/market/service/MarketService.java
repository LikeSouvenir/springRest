package com.example.demo.core.market.service;

import com.example.demo.controllers.market.dto.GetMarket;
import com.example.demo.controllers.market.dto.MarketDTO;
import com.example.demo.controllers.product.dto.FullProductListInfo;
import com.example.demo.controllers.product.dto.UpdateProductDTO;
import com.example.demo.core.market.entity.MarketEntity;
import com.example.demo.core.market.repository.MarketRepository;
import com.example.demo.core.product.entity.ProductEntity;
import com.example.demo.core.product.repository.ProductRepository;
import com.example.demo.core.productsInMarket.entity.ProductsInMarketEntity;
import com.example.demo.core.productsInMarket.repository.ProductsInMarketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MarketService {
    private final MarketRepository marketRepository;
    private final ProductRepository productRepository;
    private final ProductsInMarketRepository productsInMarketRepository;

    public MarketService(MarketRepository marketRepository,
                         ProductRepository productRepository,
                         ProductsInMarketRepository productsInMarketRepository) {
        this.marketRepository = marketRepository;
        this.productRepository = productRepository;
        this.productsInMarketRepository = productsInMarketRepository;
    }

    public void addProducts(UpdateProductDTO product) {

        // проверка на наличие магазина
        Optional<MarketEntity> marketEntity = this.findMarketById(product.getMarketId());
        if (marketEntity.isEmpty()) {
            product.setReturnStatus("Указаный магазин не найден");
            return;
        }

        Optional<ProductEntity> productEntity = this.productRepository.findProductByArticle(product.getArticleInRegister());
        // проверка на наличие артикля в системе
        if (productEntity.isEmpty()) {
            product.setReturnStatus("Указаный товар не найден");
            return;
        }

        // проверяем существует ли подобная позиция в магазине
        Optional<ProductsInMarketEntity> productsInMarketEntity = this.productsInMarketRepository.findByName(
                marketEntity.get().getId(), productEntity.get().getId()
        );
        // если - нет создаем
        if (productsInMarketEntity.isEmpty()) {
            this.productsInMarketRepository.save(new ProductsInMarketEntity(
                    product.getCount(), product.getCost(), marketEntity.get(), productEntity.get())
            );

            product.setReturnStatus("Товар в магазин добавлен");
        } else { // если да, обновляем
            this.productsInMarketRepository.updateProduct(productsInMarketEntity.get().getId(), product.getCount(), product.getCost());

            product.setReturnStatus("Товар в магазине обновлен");
        }
    }

    public MarketEntity createNewMarket (MarketDTO body) {
        return marketRepository.save(new MarketEntity(body.getName(), body.getOwner(), body.getAddress()));
    }

    public Optional<MarketEntity> findMarketById(UUID id) {
        return marketRepository.findById(id);
    }

    public List<GetMarket> findAllMarkets() {
        return marketRepository.findAllMarkets();
    }

    //найти товары в магазинах
    public List<FullProductListInfo> findMarketsPrice() {
        return productsInMarketRepository.findAllProducts();
    }

    public Optional<ProductsInMarketEntity> findProductInMarketById(UUID market, UUID product) {
        return productsInMarketRepository.findByName(market, product);
    }

    public Optional<ProductsInMarketEntity> findProductById(UUID id) {
        return productsInMarketRepository.findById(id);
    }

    //добавить товар в магазинах
    public ProductsInMarketEntity addProductInMarket(ProductsInMarketEntity product) {
        return productsInMarketRepository.save(product);
    }

    //обновить товар в магазинах
    public void updateProductInMarket(UUID id, int newCount, Double newCost) {
        productsInMarketRepository.updateProduct(id, newCount, newCost);
    }
}
