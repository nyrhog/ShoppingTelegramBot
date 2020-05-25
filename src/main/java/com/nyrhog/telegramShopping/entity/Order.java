package com.nyrhog.telegramShopping.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orderclient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"client"})
public class Order {

    @EmbeddedId
    private OrderClothesID id;

    @Column
    private Date applicationDate;

    @Column
    private Date deliveryDate;

    @Column
    //TODO: прочитать про decimal number
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderClothes> orderClothes = new ArrayList<>();

    public void addClothes(Clothes clothes, String color, String size){
        OrderClothes orderClothes = new OrderClothes(this, clothes,
                                                        color, size);
        this.orderClothes.add(orderClothes);
        clothes.getOrderClothes().add(orderClothes);
    }

    public void removeClothes(Clothes clothes){
        for (OrderClothes orderClothes: clothes.getOrderClothes()){
            if(orderClothes.getOrder().equals(this) &&
                    orderClothes.getClothes().equals(clothes))
            {
                orderClothes.getOrder().getOrderClothes().remove(orderClothes);
                orderClothes.setOrder(null);
                orderClothes.setClothes(null);
            }
        }
    }

    public Order(Double totalPrice){

        this.totalPrice = totalPrice;

    }

}
