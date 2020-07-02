package com.nyrhog.telegramShopping.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class OrderClothes {

    public OrderClothes(OrderClient order, Clothes clothes, String size, String color) {
        this.order = order;
        this.clothes = clothes;
        this.color = color;
        this.size = size;
        this.id = new OrderClothesId(order.getId(), clothes.getId());
    }

    @EmbeddedId
    private OrderClothesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private OrderClient order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clothesId")
    private Clothes clothes;

    @Column
    private String size;

    @Column
    private String color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderClothes that = (OrderClothes) o;
        return Objects.equals(order, that.order) &&
                Objects.equals(clothes, that.clothes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, clothes);
    }
}
