package com.example.demo.core.contractor.entity;


import com.example.demo.core.market.entity.MarketEntity;
import com.example.demo.core.orderItem.entity.OrderItemEntity;
import com.example.demo.utils.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contractor_entity")
public class ContractorEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_in_order_id", referencedColumnName = "id")
    private OrderItemEntity orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private MarketEntity market;// contractor

    @Column(name = "amount_count")
    private int amountCount;

    @Column(name = "amount_cost")
    private double amountPrice;

    @Column(name = "status")
    private String status = "В обработке";
}
