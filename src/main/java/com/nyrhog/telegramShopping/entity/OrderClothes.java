package com.nyrhog.telegramShopping.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class OrderClothes {

    public OrderClothes(Order order, Clothes clothes, String color, String size){
        this.order = order;
        this.clothes = clothes;
        this.color = color;
        this.size = size;
    }

    @EmbeddedId
    private OrderClothesID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clothesId")
    private Clothes clothes;

    private String size;

    private String color;
}
