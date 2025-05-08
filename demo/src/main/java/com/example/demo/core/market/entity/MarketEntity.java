package com.example.demo.core.market.entity;

import com.example.demo.controllers.market.dto.MarketDTO;
import com.example.demo.utils.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "market_entity")
public class MarketEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "address")
    private String address;

    public MarketDTO toDTO() {
        return new MarketDTO(this.name, this.owner, this.address);
    }
}
